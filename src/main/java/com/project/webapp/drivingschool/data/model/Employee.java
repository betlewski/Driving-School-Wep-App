package com.project.webapp.drivingschool.data.model;

import com.project.webapp.drivingschool.data.utils.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

/**
 * Klasa reprezentująca pracownika szkoły nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employees")
public class Employee implements UserDetails {

    /**
     * Identyfikator pracownika
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Rola pracownika:
     * wykładowca, instruktor jazdy lub administrator
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;

    /**
     * Imię i nazwisko
     */
    @Column(length = 50, nullable = false)
    private String fullName;

    /**
     * Numer telefonu komórkowego
     */
    @Pattern(regexp = "^[1-9]d{2}-d{3}-d{3}$")
    private String phoneNumber;

    /**
     * Adres email
     */
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hasło,
     * składające się z min. 8 znaków w tym: 1 cyfry,
     * 1 dużej litery, 1 małej litery i 1 znaku specjalnego
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
     * Pobranie roli użytkownika
     *
     * @return rola pracownika
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName;
        if (employeeRole.equals(EmployeeRole.ADMINISTRATOR)) {
            roleName = "ADMINISTRATOR";
        } else if (employeeRole.isLecturer()) {
            roleName = "LECTURER";
        } else if (employeeRole.isInstructor()) {
            roleName = "INSTRUCTOR";
        } else {
            roleName = "DELETED";
        }
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    /**
     * Pobranie nazwy użytkownika
     *
     * @return adres email pracownika
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Sprawdzenie, czy pracownik jest aktywny
     * tzn. czy jego rola jest różna od DELETED.
     *
     * @return true - jeśli pracownik jest aktywny, false - w przeciwnym razie
     */
    public boolean isActive() {
        return !employeeRole.equals(EmployeeRole.DELETED);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

}
