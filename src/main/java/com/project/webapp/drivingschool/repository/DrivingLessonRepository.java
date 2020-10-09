package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.DrivingLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla zajęć praktycznych (jazd szkoleniowych)
 */
@Repository
public interface DrivingLessonRepository extends JpaRepository<DrivingLesson, Long> {


}
