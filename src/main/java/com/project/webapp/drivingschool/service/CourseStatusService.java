package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.utils.CourseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serwis dla automatycznej zmiany statusu kursu nauki jazdy
 */
@Service
public class CourseStatusService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseStatusService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Automatyczna zmiana statusu kursu o podanym ID
     * na podstawie zawartych w nim danych.
     *
     * @param id ID kursu do edycji
     * @return edytowany kurs
     */
    public ResponseEntity<Course> refreshCourseStatusByCourseId(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.map(this::refreshCourseStatus)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Automatyczna zmiana statusu kursu
     * na podstawie zawartych w nim danych.
     *
     * @param course kurs do edycji
     * @return edytowany kurs
     */
    public ResponseEntity<Course> refreshCourseStatus(Course course) {
        if (course != null) {
            CourseStatus status = course.getCourseStatus();
            switch (status) {
                case MEDICAL_EXAMS:
                    course = checkStatusOnMedicalExams(course);
                    break;
                case DOCUMENTS_SUBMISSION:
                    course = checkStatusOnDocumentsSubmission(course);
                    break;
                case LECTURES:
                    course = checkStatusOnLectures(course);
                    break;
                case THEORY_INTERNAL_EXAM:
                    course = checkStatusOnTheoryInternalExam(course);
                    break;
                case DRIVING_LESSONS:
                    course = checkStatusOnDrivingLessons(course);
                    break;
                case PRACTICAL_INTERNAL_EXAM:
                    course = checkStatusOnPracticalInternalExam(course);
                    break;
                case STATE_EXAMS:
                    course = checkStatusOnStateExams(course);
                    break;
                case FINISHED:
                    course = checkStatusOnFinished(course);
                    break;
                default:
                    break;
            }
            try {
                course = courseRepository.save(course);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z MEDICAL_EXAMS.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnMedicalExams(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z DOCUMENTS_SUBMISSION.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnDocumentsSubmission(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z LECTURES.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnLectures(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z THEORY_INTERNAL_EXAM.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnTheoryInternalExam(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z DRIVING_LESSONS.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnDrivingLessons(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z PRACTICAL_INTERNAL_EXAM.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnPracticalInternalExam(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z STATE_EXAMS.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnStateExams(Course course) {
        // TODO
        return course;
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z FINISHED.
     *
     * @param course sprawdzany kurs
     * @return kurs
     */
    private Course checkStatusOnFinished(Course course) {
        // TODO
        return course;
    }

}
