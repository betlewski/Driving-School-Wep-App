package com.project.webapp.drivingschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca pojedynczy wykład prowadzony w ramach cyklu wykładów
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lectures")
public class Lecture {

    /**
     * Identyfikator wykładu
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Czas rozpoczęcia wykładu
     */
    @NotNull
    private LocalDateTime startTime;

    /**
     * Czas zakończenia wykładu
     */
    @NotNull
    private LocalDateTime endTime;

    /**
     * Dodatkowe informacje
     */
    private String additionalInfo;

}
