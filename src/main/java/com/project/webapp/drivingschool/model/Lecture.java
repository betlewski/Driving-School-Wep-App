package com.project.webapp.drivingschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
     * Data i godzina rozpoczęcia wykładu
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     * Data i godzina zakończenia wykładu
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    /**
     * Dodatkowe informacje
     */
    private String additionalInfo;

}
