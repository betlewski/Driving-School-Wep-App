package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.DrivingLesson;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.repository.DrivingLessonRepository;
import com.project.webapp.drivingschool.utils.LessonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Serwis dla zajęć praktycznych (jazd szkoleniowych)
 */
@Service
public class DrivingLessonService {

    private DrivingLessonRepository drivingLessonRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;

    @Autowired
    public DrivingLessonService(DrivingLessonRepository drivingLessonRepository,
                                CourseRepository courseRepository,
                                CourseService courseService) {
        this.drivingLessonRepository = drivingLessonRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    /**
     * Pobranie jazd szkoleniowych w ramach aktywnego kursu
     * dla kursanta o podanym ID.
     *
     * @param id ID kursanta
     * @return lista jazd
     */
    public Set<DrivingLesson> getAllDrivingLessonsByStudentId(Long id) {
        Optional<Course> activeCourse = courseService.getActiveCourseByStudentId(id);
        return activeCourse.map(Course::getDrivingLessons).orElse(new HashSet<>());
    }

    /**
     * Pobranie wszystkich jazd przeprowadzanych
     * przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista jazd
     */
    public Set<DrivingLesson> getAllDrivingLessonsByEmployeeId(Long id) {
        return drivingLessonRepository.findAllByEmployeeId(id);
    }

    /**
     * Pobranie łącznej liczby godzin jazd,
     * które zaliczono w ramach podanego kursu.
     *
     * @param course kurs
     * @return liczba godzin zaliczonych jazd
     */
    public Integer getAllPassedHoursOfDrivingLessonsByCourse(Course course) {
        return course.getDrivingLessons().stream()
                .filter(lesson -> lesson.getLessonStatus().equals(LessonStatus.PASSED))
                .mapToInt(lesson -> (int) ChronoUnit.HOURS.between(
                        lesson.getStartTime(), lesson.getEndTime()))
                .sum();
    }

    /**
     * Sprawdzenie, czy w ramach podanego kursu
     * zaliczono zajęcia praktyczne.
     *
     * @param course kurs
     * @return true - jeśli zaliczono, false - w przeciwnym razie
     */
    public Boolean isDrivingLessonsPassedByCourse(Course course) {
        Boolean answer = Boolean.FALSE;
        if (course != null) {
            Integer passedHours = getAllPassedHoursOfDrivingLessonsByCourse(course);
            Integer requiredHours = course.getLicenseCategory().practiceHours;
            if (passedHours >= requiredHours) {
                answer = Boolean.TRUE;
            }
        }
        return answer;
    }

    /**
     * Dodanie jazdy do aktywnego kursu dla kursanta o podanym ID
     *
     * @param lesson jazda do dodania
     * @param id     ID kursanta
     * @return dodana jazda
     */
    public ResponseEntity<DrivingLesson> addDrivingLesson(DrivingLesson lesson, Long id) {
        Optional<Course> optionalCourse = courseService.getActiveCourseByStudentId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            try {
                lesson.setLessonStatus(LessonStatus.REQUESTED);
                lesson = drivingLessonRepository.save(lesson);
                course.getDrivingLessons().add(lesson);
                courseRepository.save(course);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zmiana statusu przebiegu jazdy o podanym ID
     *
     * @param id     ID jazdy
     * @param status status przebiegu jazdy
     * @return edytowana jazda lub błąd
     */
    public ResponseEntity<DrivingLesson> changeLessonStatusByDrivingLessonId(Long id, LessonStatus status) {
        Optional<DrivingLesson> optionalLesson = drivingLessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            DrivingLesson lesson = optionalLesson.get();
            try {
                lesson.setLessonStatus(status);
                lesson = drivingLessonRepository.save(lesson);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
