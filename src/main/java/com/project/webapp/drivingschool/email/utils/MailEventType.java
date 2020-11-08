package com.project.webapp.drivingschool.email.utils;

/**
 * Typ wydarzenia związanego z powiadomieniem mailowym
 */
public enum MailEventType {

    LECTURE("Wykład"),

    DRIVING_LESSON("Jazda szkoleniowa"),

    THEORY_INTERNAL_EXAM("Egzamin wewnętrzny (teoretyczny)"),

    PRACTICAL_INTERNAL_EXAM("Egzamin wewnętrzny (praktyczny)");

    /**
     * Tekst przypisany do wydarzenia
     * (wykorzystywany w treści powiadomienia)
     */
    public final String text;

    MailEventType(String text) {
        this.text = text;
    }

}
