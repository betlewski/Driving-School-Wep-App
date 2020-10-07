package com.project.webapp.drivingschool.utils;

/**
 * Status przebiegu zajęć w ramach kursu nauki jazdy
 */
public enum LessonStatus {

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
     * zgłoszenie zostaje zrealizowane (zajęcia ukończone w ramach kursu)
     */
    PASSED,

    /**
     * Etap trzeci:
     * zgłoszenie nie zostaje zrealizowane (nieodbycie zajęć, brak obecności)
     */
    FAILED

}
