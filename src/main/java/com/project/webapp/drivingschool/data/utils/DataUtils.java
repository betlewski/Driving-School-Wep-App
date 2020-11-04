package com.project.webapp.drivingschool.data.utils;

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

}
