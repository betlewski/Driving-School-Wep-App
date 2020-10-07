package com.project.webapp.drivingschool.utils;

/**
 * Status przetworzenia dokumentów / zgłoszeń / płatności
 */
public enum ProcessingStatus {

    /**
     * Etap pierwszy:
     * status nieuregulowany (do wykonania)
     */
    TO_COMPLETE,

    /**
     * Etap drugi:
     * zgłoszenie uregulowania
     */
    COMPLETED,

    /**
     * Etap trzeci:
     * status uregulowany (wykonany)
     */
    ACCEPTED

}
