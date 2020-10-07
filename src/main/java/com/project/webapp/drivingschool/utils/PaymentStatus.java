package com.project.webapp.drivingschool.utils;

/**
 * Status przetworzenia płatności
 */
public enum PaymentStatus {

    /**
     * Etap pierwszy:
     * płatność nieuregulowana (do zapłaty)
     */
    TO_PAY,

    /**
     * Etap drugi:
     * zgłoszenie uregulowania płatności
     */
    PAID,

    /**
     * Etap trzeci:
     * płatność uregulowana (opłacona)
     */
    ACCEPTED

}
