package com.project.webapp.drivingschool.data.model;

import com.project.webapp.drivingschool.data.utils.PaymentType;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca płatności w ramach kursu nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "payments")
public class Payment {

    /**
     * Identyfikator płatności
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Typ płatności
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

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
     * Czas zgłoszenia zapłaty
     */
    private LocalDateTime paymentTime;

}
