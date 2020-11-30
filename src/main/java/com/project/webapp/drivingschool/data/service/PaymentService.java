package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Payment;
import com.project.webapp.drivingschool.data.repository.CourseRepository;
import com.project.webapp.drivingschool.data.repository.PaymentRepository;
import com.project.webapp.drivingschool.data.utils.CourseStatus;
import com.project.webapp.drivingschool.data.utils.ExamType;
import com.project.webapp.drivingschool.data.utils.PaymentType;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serwis dla płatności związanych z kursem nauki jazdy
 */
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;
    private InternalExamService internalExamService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          CourseRepository courseRepository,
                          CourseService courseService,
                          @Lazy InternalExamService internalExamService) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.internalExamService = internalExamService;
    }

    /**
     * Pobranie wszystkich płatności związanych z aktywnym kursem dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return zbiór płatności
     */
    public Set<Payment> getAllPaymentsForActiveCourseByEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(Course::getPayments).orElse(new HashSet<>());
    }

    /**
     * Pobranie płatności o podanym statusie przetworzenia
     * związanych z aktywnym kursem dla kursanta o podanym adresie email.
     *
     * @param email  adres email kursanta
     * @param status status przetworzenia płatności
     * @return zbiór płatności o podanym statusie
     */
    public Set<Payment> getPaymentsForActiveCourseByEmailAndProcessingStatus(String email, ProcessingStatus status) {
        Set<Payment> allPayments = getAllPaymentsForActiveCourseByEmail(email);
        return allPayments.stream()
                .filter(payment -> payment.getProcessingStatus().equals(status))
                .collect(Collectors.toSet());
    }

    /**
     * Szukanie kursu zawierającego płatność o podanym ID.
     *
     * @param id ID płatności
     * @return znaleziony kurs
     */
    public Optional<Course> findCourseByPaymentId(Long id) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getPayments().stream()
                        .map(Payment::getId)
                        .collect(Collectors.toList())
                        .contains(id))
                .findFirst();
    }

    /**
     * Dodanie płatności do aktywnego kursu dla kursanta o podanym ID.
     *
     * @param payment płatność do dodania
     * @param email   adres email kursanta
     * @return dodana płatność lub błąd
     */
    public ResponseEntity<Payment> addPayment(Payment payment, String email) {
        Optional<Course> optionalCourse = courseService.getActiveCourseByEmail(email);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            try {
                payment.setProcessingStatus(ProcessingStatus.TO_COMPLETE);
                payment = paymentRepository.save(payment);
                course.getPayments().add(payment);
                courseRepository.save(course);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Usuwanie płatności o podanym ID.
     *
     * @param id ID płatności
     * @return usunięta płatność
     */
    public ResponseEntity<Boolean> deletePayment(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            Optional<Course> optionalCourse = findCourseByPaymentId(id);
            try {
                if (optionalCourse.isPresent()) {
                    Course course = optionalCourse.get();
                    course.getPayments().remove(payment);
                    courseRepository.save(course);
                }
                paymentRepository.delete(payment);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zmiana statusu przetworzenia płatności o podanym ID
     *
     * @param id     ID płatności
     * @param status status przetworzenia płatności
     * @return edytowana płatność lub błąd
     */
    public ResponseEntity<Payment> changeProcessingStatusByPaymentId(Long id, ProcessingStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            try {
                payment.setProcessingStatus(status);
                payment = paymentRepository.save(payment);
                checkStatusAfterPaymentChangedByPaymentId(id);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zgłoszenie uregulowania płatności o podanym ID
     *
     * @param id ID płatności
     * @return zgłoszona płatność lub błąd
     */
    public ResponseEntity<Payment> requestPaymentByPaymentId(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            try {
                payment.setProcessingStatus(ProcessingStatus.REQUESTED);
                payment.setPaymentTime(LocalDate.now());
                payment = paymentRepository.save(payment);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Sprawdzenie, czy kurs zawierający płatność o podanym ID
     * spełnia wymagania, aby zmienić swój status.
     *
     * @param id ID płatności
     */
    private void checkStatusAfterPaymentChangedByPaymentId(Long id) {
        Optional<Course> optionalCourse = findCourseByPaymentId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            checkStatusAfterPaymentChanged(course);
        }
    }

    /**
     * Sprawdzenie, czy podany kurs spełnia wymagania, aby zmienić swój status.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusAfterPaymentChanged(Course course) {
        if (course != null) {
            CourseStatus status = course.getCourseStatus();
            if (status.equals(CourseStatus.PRACTICAL_INTERNAL_EXAM)
                    && checkIfAllPaymentsCompleted(course)
                    && internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL)) {
                course.setCourseStatus(CourseStatus.STATE_EXAMS);
                courseRepository.save(course);
            }
        }
    }

    /**
     * Sprawdzenie, czy w ramach podanego kursu
     * uregulowano wszystkie płatności.
     *
     * @param course kurs
     * @return true - jeśli uregulowano wszystkie płatności, false - w przeciwnym razie
     */
    public Boolean checkIfAllPaymentsCompleted(Course course) {
        Boolean answer = Boolean.FALSE;
        if (course != null) {
            Set<Payment> allPayments = course.getPayments();
            Set<Payment> notCompleted = allPayments.stream()
                    .filter(payment -> !payment.getProcessingStatus().equals(ProcessingStatus.COMPLETED))
                    .collect(Collectors.toSet());

            if (notCompleted.isEmpty()) {
                Integer coursePrice = course.getLicenseCategory().price;
                int courseFees = allPayments.stream()
                        .filter(payment -> payment.getProcessingStatus().equals(ProcessingStatus.COMPLETED))
                        .filter(payment -> payment.getPaymentType().equals(PaymentType.COURSE_FEE))
                        .mapToInt(Payment::getPrice).sum();
                if (courseFees >= coursePrice) {
                    answer = Boolean.TRUE;
                }
            }
        }
        return answer;
    }

}
