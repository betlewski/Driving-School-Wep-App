package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.TheoryLessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla zajęć teoretycznych odbywanych w ramach kursu nauki jazdy
 */
@Repository
public interface TheoryLessonsRepository extends JpaRepository<TheoryLessons, Long> {


}
