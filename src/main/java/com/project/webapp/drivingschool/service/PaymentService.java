package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla płatności związanych z kursem nauki jazdy
 */
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

}
