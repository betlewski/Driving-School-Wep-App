package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.*;
import com.project.webapp.drivingschool.data.repository.CourseRepository;
import com.project.webapp.drivingschool.data.repository.LectureSeriesRepository;
import com.project.webapp.drivingschool.data.repository.TheoryLessonsRepository;
import com.project.webapp.drivingschool.data.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private LectureSeriesRepository lectureSeriesRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;
    private LectureService lectureService;
    private StudentService studentService;

    @Autowired
    public TheoryLessonsService(TheoryLessonsRepository theoryLessonsRepository,
                                LectureSeriesRepository lectureSeriesRepository,
                                CourseRepository courseRepository,
                                CourseService courseService,
                                @Lazy LectureService lectureService,
                                StudentService studentService) {
        this.theoryLessonsRepository = theoryLessonsRepository;
        this.lectureSeriesRepository = lectureSeriesRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.lectureService = lectureService;
        this.studentService = studentService;
    }

    /**
     * Pobranie wszystkich zajęć teoretycznych
     * w ramach aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return lista zajęć teoretycznych
     */
    public Set<TheoryLessons> getAllTheoryLessonsByStudentEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(Course::getTheoryLessons).orElse(new HashSet<>());
    }

    /**
     * Pobranie na podstawie podanego statusu przebiegu zajęć teoretycznych
     * w ramach aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param email  adres email kursanta
     * @param status status przebiegu
     * @return zajęcia teoretyczne
     */
    public Set<TheoryLessons> getTheoryLessonsByStudentEmailAndLessonStatus(String email, LessonStatus status) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(course -> course.getTheoryLessons().stream()
                .filter(lesson -> lesson.getLessonStatus().equals(status))
                .collect(Collectors.toSet()))
                .orElse(new HashSet<>());
    }

    /**
     * Pobranie wszystkich zajęć teoretycznych
     * przeprowadzanych przez pracownika o podanym adresie email.
     *
     * @param email adres email pracownika
     * @return zajęcia teoretyczne
     */
    public Set<TheoryLessonsRest> getAllTheoryLessonsByEmployeeEmail(String email) {
        Set<TheoryLessonsRest> resultSet = new HashSet<>();
        theoryLessonsRepository.findAllByLectureSeriesEmployeeEmail(email)
                .forEach(lessons -> {
                    Optional<Course> optionalCourse = findCourseByTheoryLessonsId(lessons.getId());
                    if (optionalCourse.isPresent()) {
                        Optional<Student> optionalStudent = studentService.findStudentByCourse(optionalCourse.get());
                        if (optionalStudent.isPresent()) {
                            TheoryLessonsRest lessonsRest = new TheoryLessonsRest();
                            lessonsRest.setStudent(optionalStudent.get());
                            lessonsRest.setTheoryLessons(lessons);
                            resultSet.add(lessonsRest);
                        }
                    }
                });
        return resultSet;
    }

    /**
     * Pobranie aktywnych zajęć teoretycznych w ramach aktywnego kursu
     * dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return aktywne zajęcia teoretyczne
     */
    public Optional<TheoryLessons> getActiveTheoryLessonsByStudentEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.flatMap(course -> course.getTheoryLessons().stream()
                .filter(lesson -> !lesson.getLessonStatus().equals(LessonStatus.REJECTED) &&
                        !lesson.getLessonStatus().equals(LessonStatus.FAILED))
                .findFirst());
    }

    /**
     * Pobranie wszystkich aktualnych (nieodrzuconych) wykładów
     * w ramach aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return lista wykładów
     */
    public Set<Lecture> getAllLecturesForActualTheoryLessonsByStudentEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        if (activeCourse.isPresent()) {
            Optional<TheoryLessons> optionalTheory = activeCourse.get().getTheoryLessons().stream()
                    .filter(theory -> theory.getLessonStatus().equals(LessonStatus.ACCEPTED) ||
                            theory.getLessonStatus().equals(LessonStatus.PASSED))
                    .findFirst();
            if (optionalTheory.isPresent()) {
                return optionalTheory.get().getLectureSeries().getLectures();
            }
        }
        return new HashSet<>();
    }

    /**
     * Pobranie liczby godzin obecnie ukończonych
     * zajęć teoretycznych w ramach podanego kursu.
     *
     * @param course kurs
     * @return liczba godzin ukończonych zajęć teoretycznych
     */
    public Integer getCurrentlyPassedHoursOfTheoryLessonsByCourse(Course course) {
        Optional<TheoryLessons> acceptedLessons = course.getTheoryLessons().stream()
                .filter(lesson -> lesson.getLessonStatus().isActive())
                .findFirst();
        if (acceptedLessons.isPresent()) {
            Long acceptedSeriesId = acceptedLessons.get().getLectureSeries().getId();
            return lectureService.getCurrentlyPassedHoursOfLecturesByLectureSeriesId(acceptedSeriesId);
        }
        return 0;
    }

    /**
     * Szukanie kursu zawierającego zajęcia teoretyczne o podanym ID.
     *
     * @param id ID zajęć
     * @return znaleziony kurs
     */
    public Optional<Course> findCourseByTheoryLessonsId(Long id) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getTheoryLessons().stream()
                        .map(TheoryLessons::getId)
                        .collect(Collectors.toList())
                        .contains(id))
                .findFirst();
    }

    /**
     * Sprawdzenie, czy w ramach podanego kursu zaliczono zajęcia teoretyczne
     *
     * @param course kurs
     * @return true - jeśli zaliczono, false - w przeciwnym razie
     */
    public Boolean isTheoryLessonsPassedByCourse(Course course) {
        Boolean answer = Boolean.FALSE;
        if (course != null) {
            Optional<TheoryLessons> passedLessons = course.getTheoryLessons().stream()
                    .filter(lesson -> lesson.getLessonStatus().equals(LessonStatus.PASSED))
                    .findFirst();
            if (passedLessons.isPresent()) {
                Long passedSeriesId = passedLessons.get().getLectureSeries().getId();
                Integer passedHours = lectureService.getAllHoursOfLecturesByLectureSeriesId(passedSeriesId);
                Integer requiredHours = Constants.REQUIRED_THEORY_HOURS;
                if (passedHours >= requiredHours) {
                    answer = Boolean.TRUE;
                }
            }
        }
        return answer;
    }

    /**
     * Sprawdzenie, czy kursant o podanym adresie email w ramach aktywnego kursu
     * ma trwające zajęcia teoretyczne tzn. takie o statusie różnym od REJECTED i FAILED.
     * Jeśli tak - nie jest możliwe dodanie nowych zajęć teoretycznych.
     *
     * @param email adres email kursanta
     * @return true - jeśli takie zajęcia istnieją, false - w przeciwnym razie
     */
    public Boolean isTheoryLessonsActiveByStudentEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(course -> course.getTheoryLessons().stream()
                .anyMatch(lesson -> !lesson.getLessonStatus().equals(LessonStatus.REJECTED) &&
                        !lesson.getLessonStatus().equals(LessonStatus.FAILED)))
                .orElse(false);
    }

    /**
     * Dodanie zajęć teoretycznych do aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param email           adres email kursanta
     * @param lectureSeriesId ID cyklu wykładów
     * @return dodane zajęcia
     */
    public ResponseEntity<TheoryLessons> addTheoryLessons(String email, Long lectureSeriesId) {
        if (!isTheoryLessonsActiveByStudentEmail(email)) {
            Optional<Course> optionalCourse = courseService.getActiveCourseByEmail(email);
            Optional<LectureSeries> seriesOptional = lectureSeriesRepository.findById(lectureSeriesId);
            if (optionalCourse.isPresent() && seriesOptional.isPresent()) {
                Course course = optionalCourse.get();
                LectureSeries series = seriesOptional.get();
                TheoryLessons lesson = new TheoryLessons();
                try {
                    lesson.setLectureSeries(series);
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
                checkStatusAfterTheoryLessonsChangedByTheoryLessonId(id);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Sprawdzenie, czy kurs zawierający zajęcia teoretyczne
     * o podanym ID spełnia wymagania, aby zmienić swój status.
     *
     * @param id ID zajęć
     */
    private void checkStatusAfterTheoryLessonsChangedByTheoryLessonId(Long id) {
        Optional<Course> optionalCourse = findCourseByTheoryLessonsId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            checkStatusAfterTheoryLessonsChanged(course);
        }
    }

    /**
     * Sprawdzenie, czy podany kurs spełnia wymagania, aby zmienić swój status.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusAfterTheoryLessonsChanged(Course course) {
        if (course != null) {
            CourseStatus status = course.getCourseStatus();
            if (status.equals(CourseStatus.LECTURES) && isTheoryLessonsPassedByCourse(course)) {
                course.setCourseStatus(CourseStatus.THEORY_INTERNAL_EXAM);
                courseRepository.save(course);
            }
        }
    }

}
