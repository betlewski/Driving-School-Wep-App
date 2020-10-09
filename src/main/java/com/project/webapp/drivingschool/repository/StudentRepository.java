package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla kursantów (klientów szkoły nauki jazdy)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


}