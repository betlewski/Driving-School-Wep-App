package com.project.webapp.drivingschool.data.utils;

/**
 * Status przebiegu dowolnych zajęć w ramach kursu nauki jazdy
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
     * zgłoszenie zostaje zrealizowane (zajęcia odbywają się)
     */
    PASSED,

    /**
     * Etap trzeci:
     * zgłoszenie nie zostaje zrealizowane (zajęcia nie odbywają się)
     */
    FAILED;

    /**
     * Sprawdzenie, czy wydarzenie o podanym statusie
     * jest wydarzeniem aktualnym tzn. nieodrzuconym.
     *
     * @return true - jeśli wydarzenie aktualne, false - w przeciwnym razie
     */
    public boolean isActual() {
        return LessonStatus.ACCEPTED.equals(this) || LessonStatus.PASSED.equals(this)
                || LessonStatus.FAILED.equals(this);
    }

}
