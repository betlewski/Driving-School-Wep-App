package com.project.webapp.drivingschool.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dane przesyłane w odpowiedzi na uwierzytelnienie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    /**
     * Wygenerowany token
     */
    private String jwtToken;

    /**
     * Nazwa użytkownika
     */
    private String username;

    /**
     * Rola użytkownika
     */
    private String userRole;

}
