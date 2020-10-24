package com.project.webapp.drivingschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Klasa reprezentująca kursanta (klienta szkoły nauki jazdy)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "students")
public class Student implements UserDetails {

    /**
     * Identyfikator kursanta
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Imię i nazwisko
     */
    @Column(length = 50, nullable = false)
    private String fullName;

    /**
     * Numer profilu kandydata na kierowcę (PKK),
     * składający się z 20 cyfr
     */
    @Pattern(regexp = "^d{20}$")
    @Column(unique = true)
    private String pkk;

    /**
     * Data urodzenia
     */
    @NotNull
    private LocalDate birthDate;

    /**
     * Adres zamieszkania
     */
    private String address;

    /**
     * Numer telefonu komórkowego
     */
    @Pattern(regexp = "^[1-9]d{2}-d{3}-d{3}$")
    @Column(unique = true)
    private String phoneNumber;

    /**
     * Adres email
     */
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hasło,
     * składające się min. 8 znaków w tym: 1 cyfry,
     * 1 dużej litery, 1 małej litery, 1 znaku specjalnego
     */
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
    private String password;

    /**
     * Data rejestracji
     */
    @NotNull
    private LocalDate registrationDate = LocalDate.now();

    /**
     * Kursy przypisane do kursanta
     */
    @OneToMany
    private Set<Course> courses;

    /**
     * Pobranie roli użytkownika
     *
     * @return rola kursanta
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_STUDENT"));
    }

    /**
     * Pobranie nazwy użytkownika
     *
     * @return adres email kursanta
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Inne metody nadpisujące UserDetails
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
