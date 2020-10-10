package com.project.webapp.drivingschool.model;

import com.project.webapp.drivingschool.utils.EmployeeRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Klasa reprezentująca pracownika szkoły nauki jazdy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employees")
public class Employee {

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
    @Column(nullable = false, unique = true)
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

}
