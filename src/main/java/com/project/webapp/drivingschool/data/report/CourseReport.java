package com.project.webapp.drivingschool.data.report;

import com.project.webapp.drivingschool.data.utils.CourseStatus;
import com.project.webapp.drivingschool.data.utils.LicenceCategory;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Klasa do generowania raportu dla kursu.
 * Zawiera podstawowe dane, statystyki i statusy przebiegu.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseReport {

    /**
     * Nazwa kategorii prawa jazdy
     */
    private LicenceCategory category;

    /**
     * Minimalny wiek wymagany do uzyskania kategorii
     */
    private Integer requiredAge;

    /**
     * Cena podstawowa kursu
     */
    private Integer price;

    /**
     * Wymagana liczba godzin zajęć teoretycznych
     */
    private Integer theoryHours;

    /**
     * Wymagana liczba godzin zajęć praktycznych
     */
    private Integer practiceHours;

    /**
     * Status przebiegu kursu
     */
    private CourseStatus courseStatus;

    /**
     * Procent ukończenia kursu
     */
    private Integer passedCoursePercent;

    /**
     * Liczba ukończonych godzin w części teoretycznej
     */
    private Integer passedTheoryHours;

    /**
     * Liczba ukończonych godzin w części praktycznej
     */
    private Integer passedPracticeHours;

    /**
     * Status rozliczenia płatności
     */
    private ProcessingStatus paymentStatus;

    /**
     * Liczba godzin dodatkowych jazd
     */
    private Integer extraDrivingLessonsHours;

    /**
     * Data rozpoczęcia kursu
     */
    private LocalDate startDate;

    /**
     * Data zakończenia kursu
     */
    private LocalDate endTime;

    /**
     * Komentarz
     */
    private String comment;

}
