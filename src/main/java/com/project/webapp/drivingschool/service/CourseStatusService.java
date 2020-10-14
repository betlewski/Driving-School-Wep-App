package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.utils.CourseStatus;
import com.project.webapp.drivingschool.utils.ExamType;
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
    private DocumentService documentService;
    private TheoryLessonsService theoryLessonsService;
    private InternalExamService internalExamService;
    private DrivingLessonService drivingLessonService;
    private PaymentService paymentService;

    @Autowired
    public CourseStatusService(CourseRepository courseRepository,
                               DocumentService documentService,
                               TheoryLessonsService theoryLessonsService,
                               InternalExamService internalExamService,
                               DrivingLessonService drivingLessonService,
                               PaymentService paymentService) {
        this.courseRepository = courseRepository;
        this.documentService = documentService;
        this.theoryLessonsService = theoryLessonsService;
        this.internalExamService = internalExamService;
        this.drivingLessonService = drivingLessonService;
        this.paymentService = paymentService;
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
                    checkStatusOnMedicalExams(course);
                    break;
                case DOCUMENTS_SUBMISSION:
                    checkStatusOnDocumentsSubmission(course);
                    break;
                case LECTURES:
                    checkStatusOnLectures(course);
                    break;
                case THEORY_INTERNAL_EXAM:
                    checkStatusOnTheoryInternalExam(course);
                    break;
                case DRIVING_LESSONS:
                    checkStatusOnDrivingLessons(course);
                    break;
                case PRACTICAL_INTERNAL_EXAM:
                    checkStatusOnPracticalInternalExam(course);
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
     */
    private void checkStatusOnMedicalExams(Course course) {
        if (documentService.checkIfMedicalExamsCompleted(course)) {
            course.setCourseStatus(CourseStatus.DOCUMENTS_SUBMISSION);
        }
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z DOCUMENTS_SUBMISSION.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusOnDocumentsSubmission(Course course) {
        if (documentService.checkIfAllDocumentsCompleted(course)) {
            course.setCourseStatus(CourseStatus.LECTURES);
        }
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z LECTURES.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusOnLectures(Course course) {
        if (theoryLessonsService.isTheoryLessonsPassedByCourse(course)) {
            course.setCourseStatus(CourseStatus.THEORY_INTERNAL_EXAM);
        }
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z THEORY_INTERNAL_EXAM.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusOnTheoryInternalExam(Course course) {
        if (internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.THEORETICAL)) {
            course.setCourseStatus(CourseStatus.DRIVING_LESSONS);
        }
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z DRIVING_LESSONS.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusOnDrivingLessons(Course course) {
        if (drivingLessonService.isDrivingLessonsPassedByCourse(course)) {
            course.setCourseStatus(CourseStatus.PRACTICAL_INTERNAL_EXAM);
        }
    }

    /**
     * Sprawdzenie, czy kurs spełnia wymagania,
     * aby zmienić status z PRACTICAL_INTERNAL_EXAM.
     *
     * @param course sprawdzany kurs
     */
    private void checkStatusOnPracticalInternalExam(Course course) {
        if (internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL) &&
                paymentService.checkIfAllPaymentsCompleted(course)) {
            course.setCourseStatus(CourseStatus.STATE_EXAMS);
        }
    }

}
