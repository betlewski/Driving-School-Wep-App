package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Document;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.repository.CourseRepository;
import com.project.webapp.drivingschool.data.repository.DocumentRepository;
import com.project.webapp.drivingschool.data.utils.CourseStatus;
import com.project.webapp.drivingschool.data.utils.DocumentType;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serwis dla dokumentów wymaganych w trakcie kursu
 */
@Service
public class DocumentService {

    private DocumentRepository documentRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;
    private StudentService studentService;

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           CourseRepository courseRepository,
                           CourseService courseService,
                           StudentService studentService) {
        this.documentRepository = documentRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    /**
     * Pobranie wszystkich dokumentów związanych
     * z aktywnym kursem dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return zbiór dokumentów
     */
    public Set<Document> getAllDocumentsForActiveCourseByEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(Course::getDocuments).orElse(new HashSet<>());
    }

    /**
     * Pobranie dokumentów o podanym statusie przetworzenia
     * związanych z aktywnym kursem dla kursanta o podanym ID.
     *
     * @param email  adres email kursanta
     * @param status status przetworzenia dokumentu
     * @return zbiór dokumentów o podanym statusie
     */
    public Set<Document> getDocumentsForActiveCourseByEmailAndProcessingStatus(String email, ProcessingStatus status) {
        Set<Document> allDocuments = getAllDocumentsForActiveCourseByEmail(email);
        return allDocuments.stream()
                .filter(document -> document.getProcessingStatus().equals(status))
                .collect(Collectors.toSet());
    }

    /**
     * Szukanie kursu zawierającego dokument o podanym ID.
     *
     * @param id ID dokumentu
     * @return znaleziony kurs
     */
    public Optional<Course> findCourseByDocumentId(Long id) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getDocuments().stream()
                        .map(Document::getId)
                        .collect(Collectors.toList())
                        .contains(id))
                .findFirst();
    }

    /**
     * Dodanie dokumentu do aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param document dokument do dodania
     * @param email    adres email kursanta
     * @return dodany dokument lub błąd
     */
    public ResponseEntity<Document> addDocument(Document document, String email) {
        Optional<Course> optionalCourse = courseService.getActiveCourseByEmail(email);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            try {
                document.setProcessingStatus(ProcessingStatus.TO_COMPLETE);
                document = documentRepository.save(document);
                course.getDocuments().add(document);
                courseRepository.save(course);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(document, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Usuwanie dokumentu o podanym ID.
     *
     * @param id ID dokumentu
     * @return usunięty dokument
     */
    public ResponseEntity<Boolean> deleteDocument(Long id) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            Optional<Course> optionalCourse = findCourseByDocumentId(id);
            try {
                if (optionalCourse.isPresent()) {
                    Course course = optionalCourse.get();
                    course.getDocuments().remove(document);
                    courseRepository.save(course);
                }
                documentRepository.delete(document);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zmiana statusu przetworzenia dokumentu o podanym ID
     *
     * @param id     ID dokumentu
     * @param status status przetworzenia dokumentu
     * @return edytowany dokument lub błąd
     */
    public ResponseEntity<Document> changeProcessingStatusByDocumentId(Long id, ProcessingStatus status) {
        Optional<Document> documentOptional = documentRepository.findById(id);
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            try {
                document.setProcessingStatus(status);
                checkStatusAfterDocumentChangedByDocumentId(id);
                document = documentRepository.save(document);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(document, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zgłoszenie dostarczenia dokumentu o podanym ID
     *
     * @param id ID dokumentu
     * @return zgłoszony dokument lub błąd
     */
    public ResponseEntity<Document> requestDocumentByDocumentId(Long id) {
        return changeProcessingStatusByDocumentId(id, ProcessingStatus.REQUESTED);
    }

    /**
     * Sprawdzenie, czy kurs zawierający dokument o podanym ID
     * spełnia wymagania, aby zmienić swój status.
     *
     * @param id ID dokumentu
     */
    private void checkStatusAfterDocumentChangedByDocumentId(Long id) {
        Optional<Course> optionalCourse = findCourseByDocumentId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            checkStatusAfterDocumentChanged(course);
        }
    }

    /**
     * Sprawdzenie, czy podany kurs spełnia wymagania, aby zmienić swój status.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusAfterDocumentChanged(Course course) {
        if (course != null) {
            CourseStatus status = course.getCourseStatus();
            switch (status) {
                case MEDICAL_EXAMS:
                    if (checkIfMedicalExamsCompleted(course)) {
                        course.setCourseStatus(CourseStatus.DOCUMENTS_SUBMISSION);
                    }
                    break;
                case DOCUMENTS_SUBMISSION:
                    if (checkIfAllDocumentsCompleted(course)) {
                        course.setCourseStatus(CourseStatus.LECTURES);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Sprawdzenie, czy w ramach kursu o podanym ID
     * dostarczono badania lekarskie.
     *
     * @param course kurs do sprawdzenia
     * @return true - jeśli dostarczono badania lekarskie, false - w przeciwnym razie
     */
    public Boolean checkIfMedicalExamsCompleted(Course course) {
        return course.getDocuments().stream()
                .filter(document -> document.getDocumentType().equals(DocumentType.MEDICAL_EXAMS))
                .anyMatch(document -> document.getProcessingStatus().equals(ProcessingStatus.COMPLETED));
    }

    /**
     * Sprawdzenie, czy w ramach kursu o podanym ID dostarczono wszystkie dokumenty.
     *
     * @param course kurs do sprawdzenia
     * @return true - jeśli dostarczono wszystkie dokumenty, false - w przeciwnym razie
     */
    public Boolean checkIfAllDocumentsCompleted(Course course) {
        Boolean answer = Boolean.FALSE;
        if (course != null) {
            Set<Document> allDocuments = course.getDocuments();
            Set<Document> notCompleted = allDocuments.stream()
                    .filter(document -> !document.getProcessingStatus().equals(ProcessingStatus.COMPLETED))
                    .collect(Collectors.toSet());

            if (notCompleted.isEmpty()) {
                boolean pkkCompleted = allDocuments.stream()
                        .filter(document -> document.getDocumentType().equals(DocumentType.DOCUMENT_PKK))
                        .anyMatch(document -> document.getProcessingStatus().equals(ProcessingStatus.COMPLETED));
                if (pkkCompleted) {
                    Optional<Student> student = studentService.findStudentByCourse(course);
                    if (student.isPresent()) {
                        LocalDate birthDate = student.get().getBirthDate();
                        boolean parentRequired = (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now()) < 18;
                        boolean parentCompleted = allDocuments.stream()
                                .filter(document -> document.getDocumentType().equals(DocumentType.PARENT_PERMISSION))
                                .anyMatch(document -> document.getProcessingStatus().equals(ProcessingStatus.COMPLETED));
                        if (!parentRequired || parentCompleted) {
                            answer = Boolean.TRUE;
                        }
                    }
                }
            }
        }
        return answer;
    }

}
