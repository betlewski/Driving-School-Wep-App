package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Payment;
import com.project.webapp.drivingschool.data.utils.LicenceCategory;
import com.project.webapp.drivingschool.data.utils.PaymentType;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Testy jednostkowe serwisu dla płatności związanych z kursem nauki jazdy.
 */
public class PaymentServiceTest {

    private static final PaymentService paymentService =
            new PaymentService(null, null, null, null);

    @Test
    public void test_checkIfAllPaymentsCompleted_notCompleted() {
        Payment payment = new Payment();
        payment.setProcessingStatus(ProcessingStatus.REQUESTED);
        payment.setPaymentType(PaymentType.EXTRA_DRIVING_LESSON);
        Course course = new Course();
        course.setLicenseCategory(LicenceCategory.AM);
        course.setPayments(new HashSet<>(Collections.singletonList(payment)));
        Boolean actual = paymentService.checkIfAllPaymentsCompleted(course);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_checkIfAllPaymentsCompleted_notEnough() {
        Payment payment1 = new Payment();
        payment1.setProcessingStatus(ProcessingStatus.COMPLETED);
        payment1.setPaymentType(PaymentType.COURSE_FEE);
        payment1.setPrice(500);
        Payment payment2 = new Payment();
        payment2.setProcessingStatus(ProcessingStatus.COMPLETED);
        payment2.setPaymentType(PaymentType.COURSE_FEE);
        payment2.setPrice(300);
        Course course = new Course();
        course.setLicenseCategory(LicenceCategory.AM);
        course.setPayments(new HashSet<>(Arrays.asList(payment1, payment2)));
        Boolean actual = paymentService.checkIfAllPaymentsCompleted(course);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_checkIfAllPaymentsCompleted_completed() {
        Payment payment1 = new Payment();
        payment1.setProcessingStatus(ProcessingStatus.COMPLETED);
        payment1.setPaymentType(PaymentType.COURSE_FEE);
        payment1.setPrice(500);
        Payment payment2 = new Payment();
        payment2.setProcessingStatus(ProcessingStatus.COMPLETED);
        payment2.setPaymentType(PaymentType.COURSE_FEE);
        payment2.setPrice(400);
        Course course = new Course();
        course.setLicenseCategory(LicenceCategory.AM);
        course.setPayments(new HashSet<>(Arrays.asList(payment1, payment2)));
        Boolean actual = paymentService.checkIfAllPaymentsCompleted(course);
        Assert.assertTrue(actual);
    }

}
