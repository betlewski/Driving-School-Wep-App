package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.CourseStatus;
import com.project.webapp.drivingschool.utils.LicenceCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
     * Kursant przypisany do kursu
     */
    @NotNull
    @ManyToOne
    private Student student;

    /**
     * Status przebiegu kursu
     */
    @NotNull
    private CourseStatus courseStatus;

    /**
     * Zajęcia teoretyczne (wykłady) przypisane do kursu
     */
    @OneToOne
    private TheoryLessons theoryLessons;

    /**
     * Zajęcia praktyczne (jazdy szkoleniowe) przypisane do kursu
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<DrivingLesson> drivingLessons;

    /**
     * Wewnętrzny egzamin teoretyczny
     */
    @OneToOne
    private InternalExam theoryInternalExam;

    /**
     * Wewnętrzny egzamin praktyczny
     */
    @OneToOne
    private InternalExam practicalInternalExam;

    /**
     * Płatności w ramach kursu
     */
    @OneToMany
    private Set<Payment> payments;

    /**
     * Czy kurs jest aktywny?
     * Jeden kursant może mieć przypisanych wiele kursów,
     * ale tylko jeden z nich może być aktywny.
     * Kurs jest aktywny od momentu rozpoczęcia do jego zakończenia
     */
    @NotNull
    private boolean isActive = true;

}
