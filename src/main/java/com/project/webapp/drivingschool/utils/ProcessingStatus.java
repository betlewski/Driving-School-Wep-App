package com.project.webapp.drivingschool.utils;

/**
 * Status przebiegu dowolnych zajęć w ramach kursu nauki jazdy
 */
public enum ProcessingStatus {

    /**
     * Etap pierwszy:
     * zgłoszenie prośby o akceptację przez prowadzącego
     */
    REQUESTED,

    /**
     * Etap drugi:
     * zgłoszenie zostaje przyjęte i czeka na realizację
     */
    ACCEPTED,

    /**
     * Etap drugi:
     * zgłoszenie zostaje odrzucone
     */
    REJECTED,

    /**
     * Etap trzeci:
     * zgłoszenie zostaje zrealizowane (zajęcia odbywają się)
     */
    PASSED,

    /**
     * Etap trzeci:
     * zgłoszenie nie zostaje zrealizowane (zajęcia nie odbywają się)
     */
    FAILED

}
