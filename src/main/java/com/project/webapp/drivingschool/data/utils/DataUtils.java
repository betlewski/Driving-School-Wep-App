package com.project.webapp.drivingschool.data.utils;

import java.time.LocalDateTime;

/**
 * Klasa przechowująca narzędzia dla danych.
 */
public final class DataUtils {

    /**
     * Sprawdzenie, czy podane hasło jest poprawne
     * tj. ma odpowiedni poziom złożoności.
     *
     * @return true - jeśli hasło jest poprawne, false - w przeciwnym razie
     */
    public static Boolean isPasswordCorrect(String password) {
        return password == null ? Boolean.FALSE :
                password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    }

    /**
     * Sprawdzenie, czy podane godziny rozpoczęcia i zakończenia
     * są odpowiednie tj. mieszczą się w godzinach pracy
     * i godzina rozpoczęcia jest przed godziną zakończenia.
     *
     * @param startTime czas rozpoczęcia
     * @param endTime   czas zakończenia
     * @return true - jeśli godziny są odpowiednie, false - w przeciwnym razie
     */
    public static Boolean isStartAndEndTimeCorrect(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null && endTime != null && startTime.isBefore(endTime)) {
            int startHour = startTime.getHour();
            int endHour = endTime.getHour();
            return (startHour >= Constants.START_WORK_HOUR && endHour <= Constants.END_WORK_HOUR) ?
                    Boolean.TRUE : Boolean.FALSE;
        } else {
            return Boolean.FALSE;
        }
    }

}
