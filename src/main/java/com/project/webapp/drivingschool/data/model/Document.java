package com.project.webapp.drivingschool.data.model;

import com.project.webapp.drivingschool.data.utils.DocumentType;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Klasa reprezentująca dokumenty wymagane w ramach kursu nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "documents")
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
     * Data zgłoszenia dostarczenia dokumentu
     */
    private LocalDate submissionTime;

}
