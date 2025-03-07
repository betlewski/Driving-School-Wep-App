package com.project.webapp.drivingschool.security.controller;

import com.project.webapp.drivingschool.security.model.JwtResponse;
import com.project.webapp.drivingschool.security.utils.JwtTokenUtil;
import com.project.webapp.drivingschool.security.model.JwtRequest;
import com.project.webapp.drivingschool.security.service.JwtUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroler do uwierzytelniania zapytań HTTP
 */
@CrossOrigin
@RestController
public class JwtAuthenticationController {

    private final Log logger = LogFactory.getLog(this.getClass());

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
     * @return wygenerowany token lub błąd uwierzytelnienia
     */
    @PostMapping(value = "/rest/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest request) {
        UserDetails userDetails;
        try {
            authenticate(request.getUsername(), request.getPassword());
            userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtTokenUtil.generateToken(userDetails);
        String login = userDetails.getUsername();
        String userRole = userDetails.getAuthorities().stream().findFirst()
                .map(GrantedAuthority::getAuthority).orElse(null);
        JwtResponse response = new JwtResponse(token, login, userRole);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Uwierzytelnienie użytkownika.
     *
     * @param username nazwa użytkownika (adres email)
     * @param password hasło
     * @throws AuthenticationException w przypadku błędu uwierzytelnienia
     */
    private void authenticate(String username, String password) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email: " + username + " and given password are not correct");
        }
    }

    /**
     * Metoda domyślna wykorzystywana do uwierzytelnienia kursanta
     * na podstawie poprawności tokenu JWT.
     */
    @GetMapping(value = "/rest/activate/student")
    public void canActivateStudent() {
    }

    /**
     * Metoda domyślna wykorzystywana do uwierzytelnienia pracownika
     * na podstawie poprawności tokenu JWT.
     */
    @GetMapping(value = "/rest/activate/employee")
    public void canActivateEmployee() {
    }

    /**
     * Metoda domyślna wykorzystywana do uwierzytelnienia administratora
     * na podstawie poprawności tokenu JWT.
     */
    @GetMapping(value = "/rest/activate/admin")
    public void canActivateAdmin() {
    }

}
