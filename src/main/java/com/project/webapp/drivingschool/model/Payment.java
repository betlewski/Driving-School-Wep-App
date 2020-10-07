package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.ProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Klasa reprezentująca płatności w ramach kursu nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {

    /**
     * Identyfikator płatności
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Kwota obejmująca płatność
     */
    @NotNull
    @Min(0)
    private Integer price;

    /**
     * Status przetworzenia płatności
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;

    /**
     * Data i godzina zgłoszenia zapłaty
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentTime;

}
