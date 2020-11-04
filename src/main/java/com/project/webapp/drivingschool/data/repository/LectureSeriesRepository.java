package com.project.webapp.drivingschool.data.repository;

import com.project.webapp.drivingschool.data.model.LectureSeries;
import com.project.webapp.drivingschool.data.utils.LectureSeriesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repozytorium dla cyklu wykładów w szkole jazdy
 */
@Repository
public interface LectureSeriesRepository extends JpaRepository<LectureSeries, Long> {

    /**
     * Pobranie wszystkich cyklów wykładów prowadzonych
     * przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista cyklów wykładów
     */
    List<LectureSeries> findAllByEmployeeId(Long id);

    /**
     * Pobranie cyklów wykładów o podanym statusie przebiegu
     *
     * @param status status przebiegu cyklu wykładów
     * @return lista cyklów wykładów
     */
    List<LectureSeries> findAllByStatus(LectureSeriesStatus status);

}
