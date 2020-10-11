package com.project.webapp.drivingschool.utils;

/**
 * Status przetworzenia dokumentów / płatności
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
    REQUESTED,

    /**
     * Etap trzeci:
     * status uregulowany (wykonany)
     */
    COMPLETED

}
