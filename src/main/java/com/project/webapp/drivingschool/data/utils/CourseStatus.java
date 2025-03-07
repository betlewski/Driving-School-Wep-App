package com.project.webapp.drivingschool.data.utils;

/**
 * Status przebiegu kursu nauki jazdy
 */
public enum CourseStatus {

    /**
     * Przeprowadzenie badań lekarskich
     */
    MEDICAL_EXAMS,

    /**
     * Złożenie dokumentu PKK i pisemnej zgody rodziców (opcjonalnie)
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
    DRIVING_LESSONS,

    /**
     * Praktyczny egzamin wewnętrzny
     */
    PRACTICAL_INTERNAL_EXAM,

    /**
     * Egzaminy państwowe
     */
    STATE_EXAMS,

    /**
     * Odebranie prawa jazdy i zakończenie kursu
     */
    FINISHED

}
