package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.DrivingLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repozytorium dla zajęć praktycznych (jazd szkoleniowych)
 */
@Repository
public interface DrivingLessonRepository extends JpaRepository<DrivingLesson, Long> {

    /**
     * Pobranie wszystkich jazd przeprowadzanych
     * przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista jazd
     */
    Set<DrivingLesson> findAllByEmployeeId(Long id);

}
