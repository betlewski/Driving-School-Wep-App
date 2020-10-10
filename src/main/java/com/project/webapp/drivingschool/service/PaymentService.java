package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.Payment;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.repository.PaymentRepository;
import com.project.webapp.drivingschool.utils.PaymentType;
import com.project.webapp.drivingschool.utils.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private StudentService studentService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          CourseRepository courseRepository,
                          StudentService studentService) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    /**
     * Pobranie wszystkich płatności związanych z aktywnym kursem dla kursanta o podanym ID.
     * @param id ID kursanta
     * @return zbiór płatności
     */
    public Set<Payment> getAllPaymentsForActiveCourseByStudentId(Long id) {
        Optional<Course> activeCourse = studentService.getActiveCourseByStudentId(id);
        return activeCourse.map(Course::getPayments).orElse(new HashSet<>());
    }

    /**
     * Pobranie płatności o podanym statusie przetworzenia
     * związanych z aktywnym kursem dla kursanta o podanym ID.
     * @param id ID kursanta
     * @param status status przetworzenia płatności
     * @return zbiór płatności o podanym statusie
     */
    public Set<Payment> getPaymentsForActiveCourseByStudentIdAndProcessingStatus(Long id, ProcessingStatus status) {
        Set<Payment> allPayments = getAllPaymentsForActiveCourseByStudentId(id);
        return allPayments.stream()
                .filter(payment -> payment.getProcessingStatus().equals(status))
                .collect(Collectors.toSet());
    }

    /**
     * Dodanie płatności do aktywnego kursu dla kursanta o podanym ID.
     * @param payment płatność do dodania
     * @param id ID kursanta
     * @return dodana płatność lub błąd
     */
    public ResponseEntity<Payment> addPayment(Payment payment, Long id) {
        Optional<Course> optionalCourse = studentService.getActiveCourseByStudentId(id);
        if(optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            try {
                payment = paymentRepository.save(payment);
                course.getPayments().add(payment);
                courseRepository.save(course);
            } catch(Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zmiana statusu przetworzenia płatności o podanym ID
     * @param id ID płatności
     * @param status status przetworzenia płatności
     * @return edytowana płatność lub błąd
     */
    public ResponseEntity<Payment> changeProcessingStatusByPaymentId(Long id, ProcessingStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if(paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            try {
                payment.setProcessingStatus(status);
                payment = paymentRepository.save(payment);
            } catch(Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Sprawdzenie, czy uregulowano wszystkie płatności
     * związane z aktywnym kursem dla kursanta o podanym ID.
     * @param id ID kursanta
     * @return true - jeśli uregulowano wszystkie płatności, false - w przeciwnym razie
     */
    public Boolean checkIfAllPaymentsCompleted(Long id) {
        Boolean answer = Boolean.TRUE;

        Set<Payment> allPayments = getAllPaymentsForActiveCourseByStudentId(id);
        Set<Payment> notCompleted = allPayments.stream()
                .filter(payment -> !payment.getProcessingStatus().equals(ProcessingStatus.COMPLETED))
                .collect(Collectors.toSet());

        if (!notCompleted.isEmpty()) {
            answer = Boolean.FALSE;
        } else {
            Optional<Course> activeCourse = studentService.getActiveCourseByStudentId(id);
            if (activeCourse.isPresent()) {
                Integer coursePrice = activeCourse.get().getLicenseCategory().price;
                int courseFees = allPayments.stream()
                        .filter(payment -> payment.getProcessingStatus().equals(ProcessingStatus.COMPLETED))
                        .filter(payment -> payment.getPaymentType().equals(PaymentType.COURSE_FEE))
                        .mapToInt(Payment::getPrice).sum();
                if (courseFees < coursePrice) {
                    answer = Boolean.FALSE;
                }
            }
        }
        return answer;
    }

}
