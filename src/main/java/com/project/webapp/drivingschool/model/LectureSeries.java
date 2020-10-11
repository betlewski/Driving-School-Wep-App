package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.LectureSeriesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Klasa reprezentująca cykl wykładów dla wszystkich kategorii
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lecture_series")
public class LectureSeries {

    /**
     * Identyfikator cyklu wykładów
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Wykładowca prowadzący cykl
     */
    @NotNull
    @ManyToOne
    private Employee employee;

    /**
     * Status przebiegu cyklu
     */
    @NotNull
    private LectureSeriesStatus status;

    /**
     * Lista wykładów odbywanych w ramach cyklu
     */
    @OneToMany
    private Set<Lecture> lectures;

}
