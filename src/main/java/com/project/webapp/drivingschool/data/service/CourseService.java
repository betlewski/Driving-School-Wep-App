package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Document;
import com.project.webapp.drivingschool.data.model.Payment;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.repository.CourseRepository;
import com.project.webapp.drivingschool.data.repository.DocumentRepository;
import com.project.webapp.drivingschool.data.repository.PaymentRepository;
import com.project.webapp.drivingschool.data.repository.StudentRepository;
import com.project.webapp.drivingschool.data.utils.*;
import com.project.webapp.drivingschool.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Serwis dla kursu nauki jazdy
 */
@Service
public class CourseService {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private DocumentRepository documentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository,
                         PaymentRepository paymentRepository,
                         DocumentRepository documentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Pobranie aktywnego kursu dla kursanta o podanym ID.
     * Jeśli aktywnych kursów jest wiele (sytuacja nie powinna wystąpić),
     * pobierany jest ten, który został najwcześniej rozpoczęty.
     *
     * @param id ID kursanta
     * @return aktywny kurs
     */
    public Optional<Course> getActiveCourseByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.flatMap(value -> value.getCourses()
                .stream()
                .filter(Course::isActive)
                .min(Comparator.comparing(Course::getStartDate)));
    }

    /**
     * Sprawdzenie, czy kursant o podanym ID ma przypisany aktywny kurs.
     * W takim przypadku nie jest możliwy zapis nowego kursu.
     *
     * @param id ID kursanta
     * @return true - jeśli aktywny kurs istnieje, false - w przeciwnym razie
     */
    public Boolean checkExistingActiveCourseByStudentId(Long id) {
        return getActiveCourseByStudentId(id).isPresent();
    }

    /**
     * Sprawdzenie, czy student o podanym ID może rozpocząć kurs
     * o podanej kategorii jazdy biorąc pod uwagę jego wiek.
     * Możliwe jest ropoczęcie kursu na 3 miesiące przed ukończeniem
     * wymaganego w wybranej kategorii wieku.
     *
     * @param id       ID kursanta
     * @param category kategoria kursu jazdy
     * @return true - jeśli warunek jest spełniony, false - w przeciwnym razie
     */
    public Boolean checkRequiredAgeByStudentIdAndLicenceCategory(Long id, LicenceCategory category) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent() && category != null) {
            int requiredAge = category.requiredAge;
            int studentAge = (int) ChronoUnit.YEARS.between(
                    optionalStudent.get().getBirthDate(), LocalDate.now().plusMonths(3));
            return studentAge < requiredAge;
        }
        return false;
    }

    /**
     * Dodanie nowego kursu na podaną kategorię dla kursanta o podanym ID.
     *
     * @param id       ID kursanta
     * @param category kategoria kursu jazdy
     * @return dodany kurs
     */
    public ResponseEntity<Course> addCourse(Long id, LicenceCategory category) {
        if (!checkExistingActiveCourseByStudentId(id) &&
                checkRequiredAgeByStudentIdAndLicenceCategory(id, category)) {
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                Course course = new Course();
                try {
                    course.setLicenseCategory(category);
                    course.setCourseStatus(CourseStatus.MEDICAL_EXAMS);
                    initDocumentsForNewCourse(course, student);
                    initPaymentsForNewCourse(course);
                    course = courseRepository.save(course);
                    student.getCourses().add(course);
                    studentRepository.save(student);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(course, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Zakończenie kursu o podanym ID.
     *
     * @param id ID kursu do zakończenia
     * @return zakończony kurs
     */
    public ResponseEntity<Course> finishCourseByCourseId(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            try {
                course.setCourseStatus(CourseStatus.FINISHED);
                course.setEndDate(LocalDate.now());
                course.setActive(false);
                course = courseRepository.save(course);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Zmiana statusu kursu o podanym ID.
     *
     * @param id     ID kursu do zmiany
     * @param status nowy status kursu
     * @return edytowany kurs
     */
    public ResponseEntity<Course> changeStatusByCourseId(Long id, CourseStatus status) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            try {
                course.setCourseStatus(status);
                course = courseRepository.save(course);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Inicjalizacja dokumentów do dostarczenia dla podanego kursu i kursanta.
     *
     * @param course  nowy kurs
     * @param student kursant
     */
    private void initDocumentsForNewCourse(Course course, Student student) {
        if (course != null) {
            Document medicalExams = new Document();
            medicalExams.setDocumentType(DocumentType.MEDICAL_EXAMS);
            medicalExams.setProcessingStatus(ProcessingStatus.TO_COMPLETE);

            Document documentPkk = new Document();
            documentPkk.setDocumentType(DocumentType.DOCUMENT_PKK);
            documentPkk.setProcessingStatus(ProcessingStatus.TO_COMPLETE);
            Set<Document> documents = new HashSet<>(Arrays.asList(medicalExams, documentPkk));

            int studentAge = (int) ChronoUnit.YEARS.between(student.getBirthDate(), LocalDate.now());
            if (studentAge < 18) {
                Document parentPermission = new Document();
                parentPermission.setDocumentType(DocumentType.PARENT_PERMISSION);
                parentPermission.setProcessingStatus(ProcessingStatus.TO_COMPLETE);
                documents.add(parentPermission);
            }
            try {
                documentRepository.saveAll(documents);
            } finally {
                course.setDocuments(documents);
            }
        }
    }

    /**
     * Inicjalizacja płatności do uregulowania dla podanego kursu.
     *
     * @param course nowy kurs
     */
    private void initPaymentsForNewCourse(Course course) {
        if (course != null) {
            Integer instalmentsNumber = Constants.DEFAULT_INSTALMENTS_NUMBER;
            Integer instalmentPrice = course.getLicenseCategory().price / instalmentsNumber;
            Set<Payment> payments = new HashSet<>();
            for (int i = 0; i < instalmentsNumber; i++) {
                Payment payment = new Payment();
                payment.setPaymentType(PaymentType.COURSE_FEE);
                payment.setPrice(instalmentPrice);
                payment.setProcessingStatus(ProcessingStatus.TO_COMPLETE);
                payments.add(payment);
            }
            try {
                paymentRepository.saveAll(payments);
            } finally {
                course.setPayments(payments);
            }
        }
    }

}
