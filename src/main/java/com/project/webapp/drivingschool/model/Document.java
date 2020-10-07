package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.DocumentType;
import com.project.webapp.drivingschool.utils.ProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Klasa reprezentująca dokumenty wymagane w ramach kursu nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {

    /**
     * Identyfikator dokumentu
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Typ dokumentu
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    /**
     * Status przetworzenia dokumentu
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;

    /**
     * Data i godzina zgłoszenia dostarczenia dokumentu
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionTime;

}
