package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.ExamType;
import com.project.webapp.drivingschool.utils.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Klasa reprezentująca egzamin wewnętrzny przypisany do kursu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
     * Data i godzina rozpoczęcia egzaminu
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     * Data i godzina zakończenia egzaminu
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

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
