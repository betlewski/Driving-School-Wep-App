package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Klasa reprezentująca zajęcia praktyczne (jazdy szkoleniowe)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DrivingLesson {

    /**
     * Identyfikator jazdy szkoleniowej
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Instruktor prowadzący jazdę
     */
    @NotNull
    @ManyToOne
    private Employee employee;

    /**
     * Status przebiegu jazdy
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;

    /**
     * Data i godzina rozpoczęcia jazdy
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     * Data i godzina zakończenia jazdy
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

}
