package com.project.webapp.drivingschool.utils;

/**
 * Status przebiegu cyklu wykładów
 */
public enum LectureSeriesStatus {

    /**
     * Status nowy
     * (jako jedyny daje możliwość dołączenia)
     */
    NEW,

    /**
     * Status trwający
     */
    ONGOING,

    /**
     * Status ukończony
     */
    FINISHED,

    /**
     * Status odwołany
     */
    CANCELED

}
