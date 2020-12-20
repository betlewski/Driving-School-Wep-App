package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.InternalExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repozytorium dla egzaminów wewnętrznych w trakcie kursu
 */
@Repository
public interface InternalExamRepository extends JpaRepository<InternalExam, Long> {

    /**
     * Pobranie wszystkich egzaminów wewnętrznych
     * przeprowadzanych przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista egzaminów
     */
    Set<InternalExam> findAllByEmployeeId(Long id);

    /**
     * Pobranie wszystkich egzaminów wewnętrznych
     * przeprowadzanych przez pracownika o podanym adresie email.
     *
     * @param email adres email pracownika
     * @return lista egzaminów
     */
    Set<InternalExam> findAllByEmployeeEmail(String email);

}
