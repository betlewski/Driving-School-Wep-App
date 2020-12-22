package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.*;
import com.project.webapp.drivingschool.data.repository.LectureRepository;
import com.project.webapp.drivingschool.data.repository.LectureSeriesRepository;
import com.project.webapp.drivingschool.data.repository.StudentRepository;
import com.project.webapp.drivingschool.data.utils.Constants;
import com.project.webapp.drivingschool.data.utils.DataUtils;
import com.project.webapp.drivingschool.data.utils.LectureSeriesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serwis dla pojedynczych wykładów w ramach cyklu wykładów
 */
@Service
public class LectureService {

    private LectureRepository lectureRepository;
    private LectureSeriesRepository lectureSeriesRepository;
    private StudentRepository studentRepository;
    private TheoryLessonsService theoryLessonsService;

    @Autowired
    public LectureService(LectureRepository lectureRepository,
                          LectureSeriesRepository lectureSeriesRepository,
                          StudentRepository studentRepository,
                          @Lazy TheoryLessonsService theoryLessonsService) {
        this.lectureRepository = lectureRepository;
        this.lectureSeriesRepository = lectureSeriesRepository;
        this.studentRepository = studentRepository;
        this.theoryLessonsService = theoryLessonsService;
    }

    /**
     * Pobranie wszystkich wykładów
     * przeprowadzanych przez pracownika o podanym adresie email.
     *
     * @param email adres email pracownika
     * @return lista wykładów
     */
    public Set<Lecture> getAllLecturesByEmployeeEmail(String email) {
        return lectureSeriesRepository.findAllByEmployeeEmail(email).stream()
                .map(LectureSeries::getLectures)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Pobranie wszystkich wykładów
     * w ramach cyklu wykładów o podanym ID.
     *
     * @param id ID cyklu wykładów
     * @return lista wykładów
     */
    public Set<Lecture> getAllLecturesByLectureSeriesId(Long id) {
        Optional<LectureSeries> seriesOptional = lectureSeriesRepository.findById(id);
        return seriesOptional.map(LectureSeries::getLectures).orElse(new HashSet<>());
    }

    /**
     * Pobranie zbioru wolnych wykładów
     * tzn. nieprzypisanych do żadnego cyklu wykładów.
     *
     * @return zbiór wolnych wykładów
     */
    public Set<Lecture> getAllFreeLectures() {
        return lectureRepository.findAll().stream()
                .filter(lecture -> !checkExistingInLectureSeriesByLectureId(lecture.getId()))
                .collect(Collectors.toSet());
    }

    /**
     * Sprawdzenie, czy istnieje cykl wykładów,
     * do którego jest przypisany wykład o podanym ID.
     *
     * @param id ID wykładu
     * @return true - jeśli istnieje taki cykl, false - w przeciwnym razie
     */
    public Boolean checkExistingInLectureSeriesByLectureId(Long id) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(id);
        return optionalLecture.filter(lecture -> lectureSeriesRepository.findAll().stream()
                .anyMatch(series -> series.getLectures().contains(lecture)))
                .isPresent();
    }

    /**
     * Pobranie wszystkich nowych lub trwających wykładów
     * przeprowadzanych przez pracownika o podanym adresie email.
     *
     * @param email adrs email pracownika
     * @return lista wykładów
     */
    public Set<Lecture> getAllNewOrOngoingLecturesByEmployeeEmail(String email) {
        return lectureSeriesRepository.findAllByEmployeeEmail(email).stream()
                .filter(series -> series.getStatus().equals(LectureSeriesStatus.NEW) ||
                        series.getStatus().equals(LectureSeriesStatus.ONGOING))
                .map(LectureSeries::getLectures)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Pobranie łącznej liczby godzin wykładów w ramach cyklu o podanym ID.
     *
     * @param id ID cyklu wykładów
     * @return liczba godzin wykładów
     */
    public Integer getAllHoursOfLecturesByLectureSeriesId(Long id) {
        Optional<LectureSeries> seriesOptional = lectureSeriesRepository.findById(id);
        return seriesOptional.map(lectureSeries -> lectureSeries.getLectures().stream()
                .mapToInt(lecture -> (int) ChronoUnit.HOURS.between(
                        lecture.getStartTime(), lecture.getEndTime()))
                .sum())
                .orElse(0);
    }

    /**
     * Pobranie łącznej liczby godzin obecnie ukończonych
     * wykładów w ramach cyklu o podanym ID.
     *
     * @param id ID cyklu wykładów
     * @return liczba godzin wykładów
     */
    public Integer getCurrentlyPassedHoursOfLecturesByLectureSeriesId(Long id) {
        Optional<LectureSeries> seriesOptional = lectureSeriesRepository.findById(id);
        return seriesOptional.map(lectureSeries -> lectureSeries.getLectures().stream()
                .filter(lecture -> lecture.getEndTime().isBefore(LocalDateTime.now()))
                .mapToInt(lecture -> (int) ChronoUnit.HOURS.between(
                        lecture.getStartTime(), lecture.getEndTime()))
                .sum())
                .orElse(0);
    }

    /**
     * Sprawdzenie, czy suma godzin podanych wykładów
     * jest równa wymaganej liczbie godzin zajęć teoretycznych.
     *
     * @param lectures zbiór wykładów
     * @return true - jeśli jest warunek jest spełniony, false - w przeciwnym razie
     */
    public Boolean checkIfSumEqualsRequiredTheoryHoursForLecturesSet(Set<Lecture> lectures) {
        Integer theoryHoursSum = lectures.stream()
                .mapToInt(lecture -> (int) ChronoUnit.HOURS.between(
                        lecture.getStartTime(), lecture.getEndTime()))
                .sum();
        return theoryHoursSum.equals(Constants.REQUIRED_THEORY_HOURS);
    }

    /**
     * Pobranie mapy studentów z listą wykładów
     * o podanej dacie rozpoczęcia.
     *
     * @param startDate data rozpoczęcia wykładu
     * @return mapa: student - lista wykładów
     */
    public Map<Student, List<Lecture>> getMapStudentsWithActualLecturesByLectureStartDate(LocalDate startDate) {
        Map<Student, List<Lecture>> resultMap = new HashMap<>();
        studentRepository.findAll().forEach(student -> {
            List<Lecture> lecturesToMap = new ArrayList<>();
            Set<Lecture> allLectures = theoryLessonsService.getAllLecturesForActualTheoryLessonsByStudentEmail(student.getEmail());
            allLectures.stream()
                    .filter(lecture -> lecture.getStartTime().toLocalDate().isEqual(startDate))
                    .forEach(lecturesToMap::add);
            if (!lecturesToMap.isEmpty()) {
                resultMap.put(student, lecturesToMap);
            }
        });
        return resultMap;
    }

    /**
     * Dodanie wykładu
     *
     * @param lecture wykład do dodania
     * @return dodany wykład
     */
    public ResponseEntity<Lecture> addLecture(Lecture lecture) {
        if (DataUtils.isStartAndEndTimeCorrect(lecture.getStartTime(), lecture.getEndTime())) {
            try {
                lecture = lectureRepository.save(lecture);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lecture, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Edycja danych wykładu o podanym numerze ID
     *
     * @param id         ID wykładu do edycji
     * @param newLecture wykład z nowymi danymi
     * @return edytowany wykład
     */
    public ResponseEntity<Lecture> editLecture(Long id, Lecture newLecture) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(id);
        if (optionalLecture.isPresent()) {
            Lecture lecture = optionalLecture.get();
            try {
                if (DataUtils.isStartAndEndTimeCorrect(newLecture.getStartTime(), newLecture.getEndTime())) {
                    lecture.setStartTime(newLecture.getStartTime());
                    lecture.setEndTime(newLecture.getEndTime());
                }
                lecture.setAdditionalInfo(newLecture.getAdditionalInfo());
                lecture = lectureRepository.save(lecture);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lecture, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
