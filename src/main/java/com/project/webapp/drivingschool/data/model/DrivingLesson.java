package com.project.webapp.drivingschool.data.model;

import com.project.webapp.drivingschool.data.utils.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca zajęcia praktyczne (jazdy szkoleniowe) przypisane do kursu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "driving_lessons")
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
     * Czas rozpoczęcia jazdy
     */
    @NotNull
    private LocalDateTime startTime;

    /**
     * Czas zakończenia jazdy
     */
    @NotNull
    private LocalDateTime endTime;

}
