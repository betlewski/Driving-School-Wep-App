package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Lecture;
import com.project.webapp.drivingschool.data.model.LectureSeries;
import com.project.webapp.drivingschool.data.repository.EmployeeRepository;
import com.project.webapp.drivingschool.data.repository.LectureRepository;
import com.project.webapp.drivingschool.data.repository.LectureSeriesRepository;
import com.project.webapp.drivingschool.data.utils.EmployeeRole;
import com.project.webapp.drivingschool.data.utils.LectureSeriesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Serwis dla cyklu wykładów w szkole jazdy
 */
@Service
public class LectureSeriesService {

    private LectureSeriesRepository lectureSeriesRepository;
    private LectureRepository lectureRepository;
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private LectureService lectureService;

    @Autowired
    public LectureSeriesService(LectureSeriesRepository lectureSeriesRepository,
                                LectureRepository lectureRepository,
                                EmployeeRepository employeeRepository,
                                EmployeeService employeeService,
                                LectureService lectureService) {
        this.lectureSeriesRepository = lectureSeriesRepository;
        this.lectureRepository = lectureRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.lectureService = lectureService;
    }

    /**
     * Pobranie cyklów wykładów możliwych do dołączenia
     * (o statusie NEW)
     *
     * @return lista cyklu wykładów
     */
    public List<LectureSeries> getAllLectureSeriesPossibleToJoin() {
        return lectureSeriesRepository.findAllByStatus(LectureSeriesStatus.NEW);
    }

    /**
     * Pobranie wszystkich cyklów wykładów
     * przeprowadzanych przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista cyklów wykładów
     */
    public List<LectureSeries> getAllLectureSeriesByEmployeeId(Long id) {
        return lectureSeriesRepository.findAllByEmployeeId(id);
    }

    /**
     * Dodanie cyklu wykładów prowadzonego przez wykładowcę o podanym ID
     * i zawierającego wykłady o podanych ID.
     *
     * @param employeeId  ID wykładowcy
     * @param lecturesIds zbiór ID wykładów
     * @return dodany cykl wykładów
     */
    public ResponseEntity<LectureSeries> addLectureSeries(Long employeeId, Set<Long> lecturesIds) {
        Boolean isLecturer =
                employeeService.checkExistingByEmployeeIdAndEmployeeRole(employeeId, EmployeeRole.LECTURER);
        if (isLecturer) {
            Set<Lecture> lecturesSet = new HashSet<>(lectureRepository.findAllById(lecturesIds));
            Boolean sumEqualsRequiredTheoryHours =
                    lectureService.checkIfSumEqualsRequiredTheoryHoursForLecturesSet(lecturesSet);
            if (sumEqualsRequiredTheoryHours) {
                LectureSeries series = new LectureSeries();
                try {
                    series.setEmployee(employeeRepository.findById(employeeId).orElse(null));
                    series.setLectures(lecturesSet);
                    series.setStatus(LectureSeriesStatus.NEW);
                    series = lectureSeriesRepository.save(series);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(series, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Zmiana statusu przebiegu cyklu wykładów o podanym ID
     *
     * @param id     ID cyklu wykładów
     * @param status nowy status przebiegu cyklu
     * @return edytowany cykl wykładów lub błąd
     */
    public ResponseEntity<LectureSeries> changeStatusByLectureSeriesId(Long id, LectureSeriesStatus status) {
        Optional<LectureSeries> seriesOptional = lectureSeriesRepository.findById(id);
        if (seriesOptional.isPresent()) {
            LectureSeries series = seriesOptional.get();
            try {
                series.setStatus(status);
                series = lectureSeriesRepository.save(series);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(series, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
