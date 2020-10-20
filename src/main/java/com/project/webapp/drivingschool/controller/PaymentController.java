package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.Payment;
import com.project.webapp.drivingschool.service.PaymentService;
import com.project.webapp.drivingschool.utils.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Kontroler dla płatności związanych z kursem nauki jazdy
 */
@CrossOrigin
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/all/studentId")
    @ResponseBody
    public Set<Payment> getAllPaymentsForActiveCourseByStudentId(@RequestParam("id") Long id) {
        return paymentService.getAllPaymentsForActiveCourseByStudentId(id);
    }

    @GetMapping("/all/studentIdAndStatus")
    @ResponseBody
    public Set<Payment> getPaymentsForActiveCourseByStudentIdAndProcessingStatus(@RequestParam("id") Long id,
                                                                                 @RequestParam("status") ProcessingStatus status) {
        return paymentService.getPaymentsForActiveCourseByStudentIdAndProcessingStatus(id, status);
    }

    @GetMapping("/course")
    @ResponseBody
    public Optional<Course> findCourseByPaymentId(@RequestParam("id") Long id) {
        return paymentService.findCourseByPaymentId(id);
    }

    @GetMapping("/checkAll")
    @ResponseBody
    public Boolean checkIfAllPaymentsCompleted(@RequestBody Course course) {
        return paymentService.checkIfAllPaymentsCompleted(course);
    }

    @PostMapping("/add")
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment,
                                              @RequestParam("id") Long id) {
        return paymentService.addPayment(payment, id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deletePayment(@RequestParam("id") Long id) {
        return paymentService.deletePayment(id);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<Payment> changeProcessingStatusByPaymentId(@RequestParam("id") Long id,
                                                                     @RequestBody ProcessingStatus status) {
        return paymentService.changeProcessingStatusByPaymentId(id, status);
    }

}
