package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.*;
import com.project.webapp.drivingschool.data.repository.*;
import com.project.webapp.drivingschool.data.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serwis dla zajęć praktycznych (jazd szkoleniowych)
 */
@Service
public class DrivingLessonService {

    private DrivingLessonRepository drivingLessonRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private PaymentRepository paymentRepository;
    private CourseService courseService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public DrivingLessonService(DrivingLessonRepository drivingLessonRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository,
                                PaymentRepository paymentRepository,
                                CourseService courseService,
                                EmployeeRepository employeeRepository) {
        this.drivingLessonRepository = drivingLessonRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.paymentRepository = paymentRepository;
        this.courseService = courseService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Pobranie jazd szkoleniowych w ramach aktywnego kursu
     * dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return lista jazd
     */
    public Set<DrivingLesson> getAllDrivingLessonsByEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
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
     * Pobranie mapy studentów z listą zaplanowanych
     * jazd szkoleniowych o podanej dacie rozpoczęcia.
     *
     * @param startDate data rozpoczęcia jazdy
     * @return mapa: student - lista zaplanowanych jazd
     */
    public Map<Student, List<DrivingLesson>> getMapStudentsWithAcceptedDrivingLessonsByLessonStartDate(LocalDate startDate) {
        Map<Student, List<DrivingLesson>> resultMap = new HashMap<>();
        studentRepository.findAll().forEach(student -> {
            List<DrivingLesson> lessonsToMap = new ArrayList<>();
            Set<DrivingLesson> allLessons = courseService.getActiveCourseByEmail(student.getEmail())
                    .map(Course::getDrivingLessons).orElse(new HashSet<>());
            allLessons.stream()
                    .filter(lesson -> lesson.getLessonStatus().equals(LessonStatus.ACCEPTED))
                    .filter(lesson -> lesson.getStartTime().toLocalDate().isEqual(startDate))
                    .forEach(lessonsToMap::add);
            if (!lessonsToMap.isEmpty()) {
                resultMap.put(student, lessonsToMap);
            }
        });
        return resultMap;
    }

    /**
     * Szukanie kursu zawierającego jazdy szkoleniowe o podanym ID.
     *
     * @param id ID jazd
     * @return znaleziony kurs
     */
    public Optional<Course> findCourseByDrivingLessonId(Long id) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getDrivingLessons().stream()
                        .map(DrivingLesson::getId)
                        .collect(Collectors.toList())
                        .contains(id))
                .findFirst();
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
     * Dodanie jazdy prowadzonej przez instruktora o podanym adresie email
     * do aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param lesson        jazda do dodania
     * @param studentEmail  adres email kursanta
     * @param employeeEmail adres email instruktora
     * @return dodana jazda
     */
    public ResponseEntity<DrivingLesson> addDrivingLesson(DrivingLesson lesson, String studentEmail, String employeeEmail) {
        if (DataUtils.isStartAndEndTimeCorrect(lesson.getStartTime(), lesson.getEndTime())) {
            Optional<Course> optionalCourse = courseService.getActiveCourseByEmail(studentEmail);
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeEmail);
            if (optionalCourse.isPresent() && optionalEmployee.isPresent()) {
                Course course = optionalCourse.get();
                Employee employee = optionalEmployee.get();
                try {
                    lesson.setLessonStatus(LessonStatus.REQUESTED);
                    lesson.setEmployee(employee);
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
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
                checkStatusAfterDrivingLessonChangedByDrivingLessonId(id);
                checkExtraDrivingLessonPayments(lesson);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Sprawdzenie, czy kurs zawierający jazdy szkoleniowe
     * o podanym ID spełnia wymagania, aby zmienić swój status.
     *
     * @param id ID jazd
     */
    private void checkStatusAfterDrivingLessonChangedByDrivingLessonId(Long id) {
        Optional<Course> optionalCourse = findCourseByDrivingLessonId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            checkStatusAfterDrivingLessonChanged(course);
        }
    }

    /**
     * Sprawdzenie, czy podany kurs spełnia wymagania, aby zmienić swój status.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusAfterDrivingLessonChanged(Course course) {
        if (course != null) {
            CourseStatus status = course.getCourseStatus();
            if (status.equals(CourseStatus.DRIVING_LESSONS) && isDrivingLessonsPassedByCourse(course)) {
                course.setCourseStatus(CourseStatus.PRACTICAL_INTERNAL_EXAM);
                courseRepository.save(course);
            }
        }
    }

    /**
     * Naliczenie dodatkowej opłaty, jeśli podana jazda szkoleniowa
     * przekroczyła limit wymaganej liczby godzin jazd w kursie.
     *
     * @param lesson jazda szkoleniowa
     */
    private void checkExtraDrivingLessonPayments(DrivingLesson lesson) throws Exception {
        if (lesson != null && lesson.getLessonStatus().equals(LessonStatus.PASSED)) {
            Optional<Course> optionalCourse = findCourseByDrivingLessonId(lesson.getId());
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();
                Integer sum = getAllPassedHoursOfDrivingLessonsByCourse(course);
                if (sum > course.getLicenseCategory().practiceHours) {
                    Payment payment = new Payment();
                    payment.setPaymentType(PaymentType.EXTRA_DRIVING_LESSON);
                    payment.setPrice(Constants.EXTRA_DRIVING_LESSON_FEE);
                    payment.setProcessingStatus(ProcessingStatus.TO_COMPLETE);
                    try {
                        payment = paymentRepository.save(payment);
                        course.getPayments().add(payment);
                        courseRepository.save(course);
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                }
            }
        }
    }

}
