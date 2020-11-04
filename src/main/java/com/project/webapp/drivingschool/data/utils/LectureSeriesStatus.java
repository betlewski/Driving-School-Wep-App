package com.project.webapp.drivingschool.data.utils;

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
