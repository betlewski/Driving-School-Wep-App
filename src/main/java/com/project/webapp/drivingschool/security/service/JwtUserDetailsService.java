package com.project.webapp.drivingschool.security.service;

import com.project.webapp.drivingschool.data.model.Employee;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.repository.EmployeeRepository;
import com.project.webapp.drivingschool.data.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serwis dla obsługi danych użytkownika,
 * wykorzystywany w ramach zabezpieczeń REST API.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private StudentRepository studentRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public JwtUserDetailsService(StudentRepository studentRepository,
                                 EmployeeRepository employeeRepository) {
        this.studentRepository = studentRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Pobranie użytkownika na podstawie jego nazwy (adresu email)
     *
     * @param username adres email użytkownika
     * @return znaleziony użytkownik
     * @throws UsernameNotFoundException w przypadku braku użytkownika
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username).orElse(null);
        if (student != null) {
            return student;
        } else {
            Employee employee = employeeRepository.findByEmail(username).orElse(null);
            if (employee != null) {
                return employee;
            } else {
                throw new UsernameNotFoundException("User with email: " + username + " not found.");
            }
        }
    }

}
