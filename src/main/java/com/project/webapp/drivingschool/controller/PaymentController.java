package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
