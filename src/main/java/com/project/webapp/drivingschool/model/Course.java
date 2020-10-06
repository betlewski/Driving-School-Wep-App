package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.CourseStatus;
import com.project.webapp.drivingschool.utils.LicenceCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @ManyToOne
    private Student student;

    /**
     * Status procesu przebiegu kursu
     */
    @NotNull
    private CourseStatus courseStatus;

    /**
     * Czy kurs jest aktywny?
     * Jeden kursant może mieć przypisanych wiele kursów,
     * ale tylko jeden z nich może być aktywny
     */
    @NotNull
    private boolean isActive = true;

}
