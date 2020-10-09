package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla pracowników szkoły nauki jazdy
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}
