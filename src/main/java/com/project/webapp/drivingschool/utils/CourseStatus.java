package com.project.webapp.drivingschool.utils;

/**
 * Status procesu przebiegu kursu
 */
public enum CourseStatus {

    /**
     * Przeprowadzenie badań lekarskich
     */
    MEDICAL_EXAMS,

    /**
     * Złożenie dokumentu PKK
     */
    DOCUMENTS_SUBMISSION,

    /**
     * Zajęcia teoretyczne (wykłady)
     */
    LECTURES,

    /**
     * Teoretyczny egzamin wewnętrzny
     */
    THEORY_INTERNAL_EXAM,

    /**
     * Zajęcia praktyczne (jazdy szkoleniowe)
     */
    DRIVING_TRAININGS,

    /**
     * Praktyczny egzamin wewnętrzny
     */
    PRACTICE_INTERNAL_EXAM,

    /**
     * Egzaminy państwowe
     */
    STATE_EXAMS,

    /**
     * Odebranie prawa jazdy i zakończenie kursu
     */
    COURSE_ENDING

}
