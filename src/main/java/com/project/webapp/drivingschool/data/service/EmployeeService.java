package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Employee;
import com.project.webapp.drivingschool.data.repository.EmployeeRepository;
import com.project.webapp.drivingschool.data.utils.DataUtils;
import com.project.webapp.drivingschool.data.utils.EmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serwis dla pracowników szkoły nauki jazdy
 */
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Pobranie wszystkich aktualnych pracowników
     *
     * @return lista pracowników
     */
    public List<Employee> getAllActualEmployees() {
        return employeeRepository.findAll().stream()
                .filter(employee -> !employee.getEmployeeRole().equals(EmployeeRole.DELETED))
                .collect(Collectors.toList());
    }

    /**
     * Pobranie wszystkich pracowników (również usuniętych)
     *
     * @return lista pracowników
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Pobranie pracowników, którzy nie są administratorami
     *
     * @return lista pracowników
     */
    public List<Employee> getAllEmployeesNotAdmins() {
        return getAllActualEmployees().stream()
                .filter(employee -> !employee.getEmployeeRole().equals(EmployeeRole.ADMINISTRATOR))
                .collect(Collectors.toList());
    }

    /**
     * Pobranie pracowników o podanej roli
     *
     * @param employeeRole rola pracownika
     * @return lista pracowników
     */
    public List<Employee> getAllEmployeesByEmployeeRole(EmployeeRole employeeRole) {
        return employeeRepository.findAllByEmployeeRole(employeeRole);
    }

    /**
     * Pobranie pracownika na podstawie podanego adresu email
     *
     * @param email adres email
     * @return znaleziony pracownik
     */
    public Employee getEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee.orElse(null);
    }

    /**
     * Pobranie wszystkich ról pracowników
     *
     * @return lista ról pracowników
     */
    public List<EmployeeRole> getAllEmployeeRoles() {
        return Arrays.asList(EmployeeRole.values());
    }

    /**
     * Sprawdzenie, czy pracownik o podanym ID ma przypisaną podaną rolę
     *
     * @param id   ID pracownika
     * @param role rola pracownika
     * @return true - jeśli warunek jest spełniony, false - w przeciwnym razie
     */
    public Boolean checkExistingByEmployeeIdAndEmployeeRole(Long id, EmployeeRole role) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(
                employee -> employee.getEmployeeRole().equals(role))
                .orElse(false);
    }

    /**
     * Sprawdzenie, czy podany adres email już istnieje
     *
     * @param email adres email
     * @return true - jeśli adres email istnieje, false - w przeciwnym wypadku
     */
    public Boolean emailExisting(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

    /**
     * Dodanie nowego pracownika
     *
     * @param employee pracownik do dodania
     * @return dodany pracownik
     */
    public ResponseEntity<Employee> addEmployee(Employee employee) {
        if (emailExisting(employee.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (DataUtils.isPasswordCorrect(employee.getPassword())) {
            try {
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
                employee = employeeRepository.save(employee);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Edycja danych pracownika na podstawie podanego adresu email
     *
     * @param email       adres email
     * @param newEmployee pracownik ze zmienionymi danymi
     * @return edytowany pracownik
     */
    public ResponseEntity<Employee> editEmployee(String email, Employee newEmployee) {
        Optional<Employee> oldEmployee = employeeRepository.findByEmail(email);
        if (oldEmployee.isPresent()) {
            Employee employee = oldEmployee.get();
            try {
                employee.setFullName(newEmployee.getFullName());
                employee.setPhoneNumber(newEmployee.getPhoneNumber());
                employeeRepository.save(newEmployee);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(newEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zmiana hasła pracownika o podanym adresie email
     *
     * @param email       adres email
     * @param newPassword nowe hasło
     * @return edytowany pracownik
     */
    public ResponseEntity<Employee> changePassword(String email, String newPassword) {
        if (DataUtils.isPasswordCorrect(newPassword)) {
            Optional<Employee> employee = employeeRepository.findByEmail(email);
            if (employee.isPresent()) {
                Employee employeeSave = employee.get();
                try {
                    employeeSave.setPassword(passwordEncoder.encode(newPassword));
                    employeeSave = employeeRepository.save(employeeSave);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(employeeSave, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Nadawanie nowej roli pracownikowi o podanym adresie email
     * (usunięcie pracownika w przypadku roli DELETED)
     *
     * @param email   adres email
     * @param newRole nowa rola pracownika
     * @return edytowany pracownik
     */
    public ResponseEntity<Employee> changeEmployeeRole(String email, EmployeeRole newRole) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            try {
                employee.setEmployeeRole(newRole);
                employee = employeeRepository.save(employee);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
