package com.project.webapp.drivingschool.data.model;

import com.project.webapp.drivingschool.data.utils.CourseStatus;
import com.project.webapp.drivingschool.data.utils.LicenceCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * Klasa reprezentująca kurs nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "courses")
public class Course {

    /**
     * Identyfikator kursu
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Kategoria prawa jazdy
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private LicenceCategory licenseCategory;

    /**
     * Status przebiegu kursu
     */
    @NotNull
    private CourseStatus courseStatus;

    /**
     * Dokumenty wymagane w ramach kursu
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Document> documents;

    /**
     * Płatności w ramach kursu
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Payment> payments;

    /**
     * Zajęcia teoretyczne (wykłady) przypisane do kursu
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<TheoryLessons> theoryLessons;

    /**
     * Zajęcia praktyczne (jazdy szkoleniowe) przypisane do kursu
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<DrivingLesson> drivingLessons;

    /**
     * Egzaminy wewnętrzne
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<InternalExam> internalExams;

    /**
     * Data rozpoczęcia kursu
     */
    @NotNull
    private LocalDate startDate = LocalDate.now();

    /**
     * Data zakończenia kursu
     */
    private LocalDate endDate;

    /**
     * Czy kurs jest aktywny?
     * Jeden kursant może mieć przypisanych wiele kursów,
     * ale tylko jeden z nich może być aktywny.
     * Kurs jest aktywny od momentu rozpoczęcia do jego zakończenia
     */
    @NotNull
    private boolean isActive = true;

}
