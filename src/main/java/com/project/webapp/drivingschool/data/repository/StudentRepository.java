package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repozytorium dla kursantów (klientów szkoły nauki jazdy)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Pobieranie kursanta na podstawie adres email
     *
     * @param email adres email
     * @return znaleziony kursant
     */
    Optional<Student> findByEmail(String email);

}