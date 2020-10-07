package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Klasa reprezentująca zajęcia teoretyczne przypisane do kursu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "theory_lessons")
public class TheoryLessons {

    /**
     * Identyfikator zajęć
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cykl wykładów, w ramach których odbywane są zajęcia
     */
    @NotNull
    @OneToOne
    private LectureSeries lectureSeries;

    /**
     * Status przebiegu zajęć
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;

}
