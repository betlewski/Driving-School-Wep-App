package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.Employee;
import com.project.webapp.drivingschool.utils.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repozytorium dla pracowników szkoły nauki jazdy
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Pobieranie listy pracowników na podstawie podanej roli
     * @param role rola pracownika
     * @return lista pracowników
     */
    List<Employee> findAllByEmployeeRole(EmployeeRole role);

    /**
     * Pobieranie pracownika na podstawie adres email
     *
     * @param email adres email
     * @return znaleziony pracownik
     */
    Optional<Employee> findByEmail(String email);

}
