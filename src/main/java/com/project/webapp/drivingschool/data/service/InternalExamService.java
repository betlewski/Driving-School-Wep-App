package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Employee;
import com.project.webapp.drivingschool.data.model.InternalExam;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.repository.CourseRepository;
import com.project.webapp.drivingschool.data.repository.EmployeeRepository;
import com.project.webapp.drivingschool.data.repository.InternalExamRepository;
import com.project.webapp.drivingschool.data.repository.StudentRepository;
import com.project.webapp.drivingschool.data.utils.CourseStatus;
import com.project.webapp.drivingschool.data.utils.DataUtils;
import com.project.webapp.drivingschool.data.utils.ExamType;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serwis dla egzaminów wewnętrznych w trakcie kursu
 */
@Service
public class InternalExamService {

    private InternalExamRepository internalExamRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private CourseService courseService;
    private PaymentService paymentService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public InternalExamService(InternalExamRepository internalExamRepository,
                               StudentRepository studentRepository,
                               CourseRepository courseRepository,
                               CourseService courseService,
                               @Lazy PaymentService paymentService,
                               EmployeeRepository employeeRepository) {
        this.internalExamRepository = internalExamRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.paymentService = paymentService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Pobranie wszystkich egzaminów wewnętrznych
     * w ramach aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return lista egzaminów
     */
    public Set<InternalExam> getAllInternalExamsByEmail(String email) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(course -> new HashSet<>(course.getInternalExams()))
                .orElse(new HashSet<>());
    }

    /**
     * Pobranie egzaminów wewnętrznych o podanym typie
     * w ramach aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @param type  typ egzaminu
     * @return lista egzaminów
     */
    public Set<InternalExam> getAllInternalExamsByEmailAndExamType(String email, ExamType type) {
        Optional<Course> activeCourse = courseService.getActiveCourseByEmail(email);
        return activeCourse.map(course -> course.getInternalExams().stream()
                .filter(exam -> exam.getExamType().equals(type))
                .collect(Collectors.toSet()))
                .orElse(new HashSet<>());
    }

    /**
     * Pobranie wszystkich egzaminów wewnętrznych
     * przeprowadzanych przez pracownika o podanym ID.
     *
     * @param id ID pracownika
     * @return lista egzaminów
     */
    public Set<InternalExam> getAllInternalExamsByEmployeeId(Long id) {
        return internalExamRepository.findAllByEmployeeId(id);
    }

    /**
     * Pobranie mapy studentów z listą zaplanowanych
     * egzaminów wewnętrznych o podanej dacie rozpoczęcia.
     *
     * @param startDate data rozpoczęcia egzaminu
     * @return mapa: student - lista zaplanowanych egzaminów
     */
    public Map<Student, List<InternalExam>> getMapStudentsWithAcceptedInternalExamsByExamStartDate(LocalDate startDate) {
        Map<Student, List<InternalExam>> resultMap = new HashMap<>();
        studentRepository.findAll().forEach(student -> {
            List<InternalExam> examsToMap = new ArrayList<>();
            Set<InternalExam> allExams = courseService.getActiveCourseByEmail(student.getEmail())
                    .map(Course::getInternalExams).orElse(new HashSet<>());
            allExams.stream()
                    .filter(exam -> exam.getLessonStatus().equals(LessonStatus.ACCEPTED))
                    .filter(exam -> exam.getStartTime().toLocalDate().isEqual(startDate))
                    .forEach(examsToMap::add);
            if (!examsToMap.isEmpty()) {
                resultMap.put(student, examsToMap);
            }
        });
        return resultMap;
    }

    /**
     * Szukanie kursu zawierającego egzamin wewnętrzny o podanym ID.
     *
     * @param id ID egzaminu
     * @return znaleziony kurs
     */
    public Optional<Course> findCourseByInternalExamId(Long id) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getInternalExams().stream()
                        .map(InternalExam::getId)
                        .collect(Collectors.toList())
                        .contains(id))
                .findFirst();
    }

    /**
     * Sprawdzenie, czy w ramach podanego kursu zaliczono
     * egzamin wewnętrzny o podanym typie.
     *
     * @param course kurs
     * @param type   typ egzaminu
     * @return true - jeśli zaliczono, false - w przeciwnym razie
     */
    public Boolean isInternalExamPassedByCourseAndExamType(Course course, ExamType type) {
        if (course != null) {
            return course.getInternalExams().stream()
                    .filter(exam -> exam.getExamType().equals(type))
                    .anyMatch(InternalExam::getIsPassed);
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Dodanie egzaminu prowadzonego przez egzaminatora o podanym adresie email
     * do aktywnego kursu dla kursanta o podanym adresie email.
     *
     * @param exam          egzamin do dodania
     * @param studentEmail  adres email kursanta
     * @param employeeEmail adres email egzaminatora
     * @return dodany egzamin
     */
    public ResponseEntity<InternalExam> addExam(InternalExam exam, String studentEmail, String employeeEmail) {
        if (DataUtils.isStartAndEndTimeCorrect(exam.getStartTime(), exam.getEndTime())) {
            Optional<Course> optionalCourse = courseService.getActiveCourseByEmail(studentEmail);
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeEmail);
            if (optionalCourse.isPresent() && optionalEmployee.isPresent()) {
                Course course = optionalCourse.get();
                Employee employee = optionalEmployee.get();
                try {
                    exam.setLessonStatus(LessonStatus.REQUESTED);
                    exam.setEmployee(employee);
                    exam = internalExamRepository.save(exam);
                    course.getInternalExams().add(exam);
                    courseRepository.save(course);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(exam, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Edycja danych egzaminu o podanym ID
     *
     * @param id      ID egzaminu
     * @param newExam egzamin z nowymi danymi
     * @return edytowany egzamin
     */
    public ResponseEntity<InternalExam> editExam(Long id, InternalExam newExam) {
        Optional<InternalExam> oldExam = internalExamRepository.findById(id);
        if (oldExam.isPresent()) {
            InternalExam exam = oldExam.get();
            try {
                exam.setLessonStatus(newExam.getLessonStatus());
                exam.setResult(newExam.getResult());
                exam.setIsPassed(newExam.getIsPassed());
                exam = internalExamRepository.save(exam);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Zmiana statusu przebiegu egzaminu o podanym ID
     *
     * @param id     ID egzaminu
     * @param status status przebiegu egzaminu
     * @return edytowany egzamin lub błąd
     */
    public ResponseEntity<InternalExam> changeLessonStatusByExamId(Long id, LessonStatus status) {
        Optional<InternalExam> optionalExam = internalExamRepository.findById(id);
        if (optionalExam.isPresent()) {
            InternalExam exam = optionalExam.get();
            try {
                exam.setLessonStatus(status);
                exam = internalExamRepository.save(exam);
                checkStatusAfterInternalExamChangedByInternalExamId(id);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Sprawdzenie, czy kurs zawierający egzamin wewnętrzny o podanym ID
     * spełnia wymagania, aby zmienić swój status.
     *
     * @param id ID egzaminu
     */
    private void checkStatusAfterInternalExamChangedByInternalExamId(Long id) {
        Optional<Course> optionalCourse = findCourseByInternalExamId(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            checkStatusAfterInternalExamChanged(course);
        }
    }

    /**
     * Sprawdzenie, czy podany kurs spełnia wymagania, aby zmienić swój status.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusAfterInternalExamChanged(Course course) {
        if (course != null) {
            CourseStatus status = course.getCourseStatus();
            switch (status) {
                case THEORY_INTERNAL_EXAM:
                    if (isInternalExamPassedByCourseAndExamType(course, ExamType.THEORETICAL)) {
                        course.setCourseStatus(CourseStatus.DRIVING_LESSONS);
                        courseRepository.save(course);
                    }
                    break;
                case PRACTICAL_INTERNAL_EXAM:
                    if (isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL) &&
                            paymentService.checkIfAllPaymentsCompleted(course)) {
                        course.setCourseStatus(CourseStatus.STATE_EXAMS);
                        courseRepository.save(course);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
