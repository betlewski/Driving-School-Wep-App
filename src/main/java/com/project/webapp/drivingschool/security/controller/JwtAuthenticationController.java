package com.project.webapp.drivingschool.security.controller;

import com.project.webapp.drivingschool.security.utils.JwtTokenUtil;
import com.project.webapp.drivingschool.security.model.JwtRequest;
import com.project.webapp.drivingschool.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler do uwierzytelniania zapytań HTTP
 */
@CrossOrigin
@RestController
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Pobranie tokenu JWT po pomyślnym uwierzytelnieniu.
     *
     * @param request zapytanie HTTP
     * @return wygenerowany token
     * @throws Exception w przypadku błędu uwierzytelnienia
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Uwierzytelnienie użtykownia.
     *
     * @param username nazwa użytkownika (adres email)
     * @param password hasło
     * @throws Exception w przypadku błędu uwierzytelnienia
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled: ", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials: ", e);
        }
    }

}
