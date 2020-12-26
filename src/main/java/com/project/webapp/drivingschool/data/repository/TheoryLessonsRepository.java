package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.TheoryLessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repozytorium dla zajęć teoretycznych odbywanych w ramach kursu nauki jazdy
 */
@Repository
public interface TheoryLessonsRepository extends JpaRepository<TheoryLessons, Long> {

    /**
     * Pobranie wszystkich zajęć przeprowadzanych
     * przez pracownika o podanym adresie email.
     *
     * @param email adres email pracownika
     * @return lista zajęć
     */
    Set<TheoryLessons> findAllByLectureSeriesEmployeeEmail(String email);

}
