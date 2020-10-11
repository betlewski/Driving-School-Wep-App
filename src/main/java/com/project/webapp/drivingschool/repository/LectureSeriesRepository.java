package com.project.webapp.drivingschool.repository;

import com.project.webapp.drivingschool.model.LectureSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium dla cyklu wykładów w szkole jazdy
 */
@Repository
public interface LectureSeriesRepository extends JpaRepository<LectureSeries, Long> {

    /**
     * Pobranie wszystkich wykładów prowadzonych
     * przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista wykładów
     */
    List<LectureSeries> findAllByEmployeeId(Long id);

}
