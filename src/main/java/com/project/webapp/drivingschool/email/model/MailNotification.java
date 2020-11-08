package com.project.webapp.drivingschool.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca powiadomienie mailowe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailNotification {

    /**
     * Odbiorca powiadomienia
     */
    private String mailTo;

    /**
     * Temat powiadomienia
     */
    private String subject;

    /**
     * Treść powiadomienia
     */
    private String text;

}
