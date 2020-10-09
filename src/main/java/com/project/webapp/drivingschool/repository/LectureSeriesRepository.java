package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.LectureSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dla cyklu wykładów w szkole jazdy
 */
@Repository
public interface LectureSeriesRepository extends JpaRepository<LectureSeries, Long> {


}
