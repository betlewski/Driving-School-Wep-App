package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla kursu nauki jazdy
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


}
