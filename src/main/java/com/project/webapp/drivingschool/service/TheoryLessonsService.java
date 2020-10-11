package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.*;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.repository.EmployeeRepository;
import com.project.webapp.drivingschool.repository.TheoryLessonsRepository;
import com.project.webapp.drivingschool.utils.LessonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serwis dla zajęć teoretycznych odbywanych w ramach kursu nauki jazdy
 */
@Service
public class TheoryLessonsService {

    private TheoryLessonsRepository theoryLessonsRepository;
    private CourseRepository courseRepository;
    private EmployeeRepository employeeRepository;
    private CourseService courseService;

    @Autowired
    public TheoryLessonsService(TheoryLessonsRepository theoryLessonsRepository,
                                CourseRepository courseRepository,
                                EmployeeRepository employeeRepository,
                                CourseService courseService) {
        this.theoryLessonsRepository = theoryLessonsRepository;
        this.courseRepository = courseRepository;
        this.employeeRepository = employeeRepository;
        this.courseService = courseService;
    }

    /**
     * Pobranie zajęć teoretycznych w ramach aktywnego kursu
     * dla kursanta o podanym ID.
     *
     * @param id ID kursanta
     * @return lista zajęć teoretycznych
     */
    public Set<TheoryLessons> getAllTheoryLessonsByStudentId(Long id) {
        Optional<Course> activeCourse = courseService.getActiveCourseByStudentId(id);
        return activeCourse.map(Course::getTheoryLessons).orElse(new HashSet<>());
    }

    /**
     * Pobranie liczby godzin obecnie ukończonych zajęć teoretycznych
     * w ramach aktywnego kursu dla kursanta o podanym ID.
     *
     * @param id ID kursanta
     * @return liczba godzin ukończonych zajęć teoretycznych
     */
    public Integer getCurrentlyPassedHoursOfTheoryLessonsByStudentId(Long id) {
        Optional<Course> activeCourse = courseService.getActiveCourseByStudentId(id);
        if (activeCourse.isPresent()) {
            Optional<TheoryLessons> acceptedLessons = activeCourse.get().getTheoryLessons().stream()
                    .filter(lesson -> lesson.getLessonStatus().equals(LessonStatus.ACCEPTED))
                    .findFirst();
            if (acceptedLessons.isPresent()) {
                Long acceptedSeriesId = acceptedLessons.get().getLectureSeries().getId();
                // TODO: pobranie liczby godzin obecnie ukończonych zajęć teoretycznych
                // return getCurrentlyPassedHoursOfLecturesByLectureSeriesId(acceptedSeriesId);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Pobranie wszystkich zajęć teoretycznych
     * przeprowadzanych przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista zajęć teoretycznych
     */
    public Set<TheoryLessons> getAllTheoryLessonsByEmployeeId(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return theoryLessonsRepository.findAll().stream()
                    .filter(lesson -> lesson.getLectureSeries().getEmployee().equals(employee))
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Sprawdzenie, czy kursant o podanym ID
     * zaliczył zajęcia teoretyczne w ramach aktywnego kursu.
     *
     * @param id ID kursanta
     * @return true - jeśli zaliczył, false - w przeciwnym razie
     */
    public Boolean isTheoryLessonsPassedByStudentId(Long id) {
        Boolean answer = Boolean.TRUE;
        Optional<Course> activeCourse = courseService.getActiveCourseByStudentId(id);
        if (activeCourse.isPresent()) {
            Optional<TheoryLessons> passedLessons = activeCourse.get().getTheoryLessons().stream()
                    .filter(lesson -> lesson.getLessonStatus().equals(LessonStatus.PASSED))
                    .findFirst();
            if (passedLessons.isPresent()) {
                Long passedSeriesId = passedLessons.get().getLectureSeries().getId();
                // TODO: pobranie liczby godzin w ukończonym cyklu wykładów
                Integer passedHours = 0; // = getAllHoursOfLecturesByLectureSeriesId(passedSeriesId);
                Integer requiredHours = activeCourse.get().getLicenseCategory().theoryHours;
                if (passedHours < requiredHours) {
                    answer = Boolean.FALSE;
                }
            } else {
                answer = Boolean.FALSE;
            }
        }
        return answer;
    }

    /**
     * Sprawdzenie, czy kursant o podanym ID w ramach aktywnego kursu
     * ma trwające zajęcia teoretyczne tzn. takie o statusie różnym od REJECTED i FAILED.
     * Jeśli tak - nie jest możliwe dodanie nowych zajęć teoretycznych.
     *
     * @param id ID kursanta
     * @return true - jeśli takie zajęcia istnieją, false - w przeciwnym razie
     */
    public Boolean isTheoryLessonsActiveByStudentId(Long id) {
        Optional<Course> activeCourse = courseService.getActiveCourseByStudentId(id);
        return activeCourse.map(course -> course.getTheoryLessons().stream()
                .anyMatch(lesson -> !lesson.getLessonStatus().equals(LessonStatus.REJECTED) &&
                        !lesson.getLessonStatus().equals(LessonStatus.FAILED)))
                .orElse(false);
    }

    /**
     * Dodanie zajęć teoretycznych do aktywnego kursu dla kursanta o podanym ID
     *
     * @param lesson zajęcia do dodania
     * @param id     ID kursanta
     * @return dodane zajęcia
     */
    public ResponseEntity<TheoryLessons> addTheoryLessons(TheoryLessons lesson, Long id) {
        if (!isTheoryLessonsActiveByStudentId(id)) {
            Optional<Course> optionalCourse = courseService.getActiveCourseByStudentId(id);
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();
                try {
                    lesson.setLessonStatus(LessonStatus.REQUESTED);
                    lesson = theoryLessonsRepository.save(lesson);
                    course.getTheoryLessons().add(lesson);
                    courseRepository.save(course);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(lesson, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Zmiana statusu przebiegu zajęć teoretycznych o podanym ID
     *
     * @param id     ID zajęć teoretycznych
     * @param status status przebiegu zajęć teoretycznych
     * @return edytowane zajęcia teoretyczne lub błąd
     */
    public ResponseEntity<TheoryLessons> changeLessonStatusByTheoryLessonsId(Long id, LessonStatus status) {
        Optional<TheoryLessons> optionalLesson = theoryLessonsRepository.findById(id);
        if (optionalLesson.isPresent()) {
            TheoryLessons lesson = optionalLesson.get();
            try {
                lesson.setLessonStatus(status);
                lesson = theoryLessonsRepository.save(lesson);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
