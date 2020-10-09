package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla pojedynczych wykładów w ramach cyklu wykładów
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {


}
