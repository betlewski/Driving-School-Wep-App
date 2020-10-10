package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.Document;
import com.project.webapp.drivingschool.model.Student;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.repository.DocumentRepository;
import com.project.webapp.drivingschool.repository.StudentRepository;
import com.project.webapp.drivingschool.utils.DocumentType;
import com.project.webapp.drivingschool.utils.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private StudentService studentService;

    @Autowired
    public DocumentService(DocumentRepository documentRepository,
                           StudentRepository studentRepository,
                           CourseRepository courseRepository,
                           StudentService studentService) {
        this.documentRepository = documentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    /**
     * Pobranie wszystkich dokumentów związanych z aktywnym kursem dla kursanta o podanym ID.
     *
     * @param id ID kursanta
     * @return zbiór dokumentów
     */
    public Set<Document> getAllDocumentsForActiveCourseByStudentId(Long id) {
        Optional<Course> activeCourse = studentService.getActiveCourseByStudentId(id);
        return activeCourse.map(Course::getDocuments).orElse(new HashSet<>());
    }

    /**
     * Pobranie dokumentów o podanym statusie przetworzenia
     * związanych z aktywnym kursem dla kursanta o podanym ID.
     *
     * @param id     ID kursanta
     * @param status status przetworzenia dokumentu
     * @return zbiór dokumentów o podanym statusie
     */
    public Set<Document> getDocumentsForActiveCourseByStudentIdAndProcessingStatus(Long id, ProcessingStatus status) {
        Set<Document> allDocuments = getAllDocumentsForActiveCourseByStudentId(id);
        return allDocuments.stream()
                .filter(document -> document.getProcessingStatus().equals(status))
                .collect(Collectors.toSet());
    }

    /**
     * Dodanie dokumentu do aktywnego kursu dla kursanta o podanym ID.
     *
     * @param document dokument do dodania
     * @param id       ID kursanta
     * @return dodany dokument lub błąd
     */
    public ResponseEntity<Document> addDocument(Document document, Long id) {
        Optional<Course> optionalCourse = studentService.getActiveCourseByStudentId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            try {
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
     * Sprawdzenie, czy dostarczono wszystkie dokumenty
     * związane z aktywnym kursem dla kursanta o podanym ID.
     *
     * @param id ID kursanta
     * @return true - jeśli dostarczono wszystkie dokumenty, false - w przeciwnym razie
     */
    public Boolean checkIfAllDocumentsCompleted(Long id) {
        Boolean answer = Boolean.TRUE;

        Set<Document> allDocuments = getAllDocumentsForActiveCourseByStudentId(id);
        Set<Document> notCompleted = allDocuments.stream()
                .filter(document -> !document.getProcessingStatus().equals(ProcessingStatus.COMPLETED))
                .collect(Collectors.toSet());

        if (!notCompleted.isEmpty()) {
            answer = Boolean.FALSE;
        } else {
            Optional<Course> activeCourse = studentService.getActiveCourseByStudentId(id);
            if (activeCourse.isPresent()) {
                boolean medicalCompleted = allDocuments.stream()
                        .filter(document -> !document.getDocumentType().equals(DocumentType.MEDICAL_EXAMS))
                        .anyMatch(document -> !document.getProcessingStatus().equals(ProcessingStatus.COMPLETED));
                if (!medicalCompleted) {
                    answer = Boolean.FALSE;
                } else {
                    boolean pkkCompleted = allDocuments.stream()
                            .filter(document -> !document.getDocumentType().equals(DocumentType.DOCUMENT_PKK))
                            .anyMatch(document -> !document.getProcessingStatus().equals(ProcessingStatus.COMPLETED));
                    if (!pkkCompleted) {
                        answer = Boolean.FALSE;
                    } else {
                        Optional<Student> student = studentRepository.findById(id);
                        if (student.isPresent()) {
                            LocalDate birthDate = student.get().getBirthDate()
                                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            boolean parentRequired = Period.between(birthDate, LocalDate.now()).getYears() < 18;
                            boolean parentCompleted = allDocuments.stream()
                                    .filter(document -> !document.getDocumentType().equals(DocumentType.PARENT_PERMISSION))
                                    .anyMatch(document -> !document.getProcessingStatus().equals(ProcessingStatus.COMPLETED));
                            if (parentRequired && !parentCompleted) {
                                answer = Boolean.FALSE;
                            }
                        }
                    }
                }
            }
        }
        return answer;
    }

}
