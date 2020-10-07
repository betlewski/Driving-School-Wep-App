package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
     * Status przetworzenia płatności
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    /**
     * Data i godzina ostatniej modyfikacji statusu przetworzenia
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

}
