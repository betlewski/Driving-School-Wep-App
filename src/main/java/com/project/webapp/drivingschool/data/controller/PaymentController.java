package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Payment;
import com.project.webapp.drivingschool.data.service.PaymentService;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
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
@RequestMapping("/rest/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/all/byStudentId")
    @ResponseBody
    public Set<Payment> getAllPaymentsForActiveCourseByStudentId(@RequestParam("id") Long id) {
        return paymentService.getAllPaymentsForActiveCourseByStudentId(id);
    }

    @GetMapping("/all/byStudentId/byStatus")
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

    @GetMapping("/isCompleted/byCourse")
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

    @PutMapping("/edit/status")
    public ResponseEntity<Payment> changeProcessingStatusByPaymentId(@RequestParam("id") Long id,
                                                                     @RequestBody ProcessingStatus status) {
        return paymentService.changeProcessingStatusByPaymentId(id, status);
    }

}
