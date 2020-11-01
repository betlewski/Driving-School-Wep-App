package com.project.webapp.drivingschool.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca dane użytkownika przesyłane w każdym zapytaniu HTTP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    /**
     * Nazwa użytkownika
     */
    private String username;

    /**
     * Hasło
     */
    private String password;

}
