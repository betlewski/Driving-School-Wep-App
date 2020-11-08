package com.project.webapp.drivingschool.email.utils;

import java.time.format.DateTimeFormatter;

/**
 * Klasa definiująca wyrażenia stałe
 * związane z powiadomieniami mailowymi
 */
public final class MailConstants {

    /**
     * Wyrażenie określające czas uruchomienia zadania cyklicznego
     * generującego wysyłkę powiadomień mailowych
     * (domyślnie: codziennie o godz. 06:00)
     */
    public static final String SEND_MAIL_JOB_CRON = "0 0 6 * * ?";

    /**
     * Liczba dni poprzedzających datę wydarzenia,
     * na które ma zostać wysłane powiadomienie mailowe
     */
    public static final Integer NUMBER_OF_DAYS_BEFORE_EVENT_TO_SEND = 1;

    /**
     * Sposób formatowania daty i czasu
     * wysyłanych w treści powiadomienia
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Adres URL aplikacji
     */
    private static final String APP_URL_ADDRESS = "http://localhost:8080/";

    /**
     * Wyrażenie zastępowane przez dane właściwe:
     * typ wydarzenia
     */
    public static final String EVENT_TYPE_HASH = "#eventType";

    /**
     * Wyrażenie zastępowane przez dane właściwe:
     * czas rozpoczęcia
     */
    public static final String START_TIME_HASH = "#startTime";

    /**
     * Wyrażenie zastępowane przez dane właściwe:
     * czas zakończenia
     */
    public static final String END_TIME_HASH = "#endTime";

    /**
     * Wyrażenie zastępowane przez dane właściwe:
     * dane dodatkowe
     */
    public static final String ADD_DATA_HASH = "#addData";

    /**
     * Temat powiadomienia
     */
    public static final String SUBJECT = "Przypomnienie o zbliżającym się wydarzeniu: " + EVENT_TYPE_HASH;

    /**
     * Treść powiadomienia
     */
    public static final String TEXT = "Witamy!<br>"
            + "Pragniemy przypomnieć o zbliżającym się wydarzeniu, w którym bierzesz udział.<br><br>"
            + "<b>Typ wydarzenia:</b> " + EVENT_TYPE_HASH + "<br>"
            + "<b>Czas rozpoczęcia:</b> " + START_TIME_HASH + "<br>"
            + "<b>Czas zakończenia:</b> " + END_TIME_HASH + "<br>"
            + ADD_DATA_HASH
            + "<br>Zaloguj się w aplikacji <a href=\"" + APP_URL_ADDRESS + "\">Szkoła nauki jazdy</a>, by dowiedzieć się więcej.<br><br>"
            + "Liczymy na Twoją obecność.<br>"
            + "Do zobaczenia!<br><br>"
            + "<i>Zespół szkoły nauki jazdy</i>";

}
