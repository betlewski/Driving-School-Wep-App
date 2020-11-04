package com.project.webapp.drivingschool.data.model;

import com.project.webapp.drivingschool.data.utils.ExamType;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca egzamin wewnętrzny przypisany do kursu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "internal_exams")
public class InternalExam {

    /**
     * Identyfikator egzaminu
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Typ egzaminu
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ExamType examType;

    /**
     * Pracownik prowadzący egzamin
     */
    @NotNull
    @ManyToOne
    private Employee employee;

    /**
     * Czas rozpoczęcia egzaminu
     */
    @NotNull
    private LocalDateTime startTime;

    /**
     * Czas zakończenia egzaminu
     */
    @NotNull
    private LocalDateTime endTime;

    /**
     * Status przebiegu egzaminu
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;

    /**
     * Wynik egzaminu (w procentach)
     */
    @Min(0)
    @Max(100)
    private Integer result;

    /**
     * Czy egzamin został zaliczony?
     */
    private Boolean isPassed;

}
