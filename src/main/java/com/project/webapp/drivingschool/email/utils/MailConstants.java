package com.project.webapp.drivingschool.email.utils;

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
     * Adres URL aplikacji
     */
    private static final String APP_URL_ADDRESS = "http://localhost:8080/";

    /**
     * Temat powiadomienia
     */
    public static final String SUBJECT = "Przypomnienie o zbliżającym się wydarzeniu: #eventType";

    /**
     * Treść powiadomienia
     */
    public static final String TEXT = "Witamy!<br>" +
            "Pragniemy przypomnieć o zbliżającym się wydarzeniu, w którym bierzesz udział.<br><br>" +
            "<b>Typ wydarzenia:</b> #eventType<br>" +
            "<b>Czas rozpoczęcia:</b> #startTime<br>" +
            "<b>Czas zakończenia:</b> #endTime<br>" +
            "#addData" +
            "<br>Zaloguj się w aplikacji <a href=\"" + APP_URL_ADDRESS + "\">Szkoła nauki jazdy</a>, by dowiedzieć się więcej.<br><br>" +
            "Liczymy na Twoją obecność.<br>" +
            "Do zobaczenia!<br><br>" +
            "<i>Zespół szkoły nauki jazdy</i>";

}
