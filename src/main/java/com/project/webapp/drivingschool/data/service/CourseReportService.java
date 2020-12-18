package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.report.CourseReport;
import com.project.webapp.drivingschool.data.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serwis dla raportu z kursu
 */
@Service
public class CourseReportService {

    private CourseService courseService;
    private TheoryLessonsService theoryLessonsService;
    private DrivingLessonService drivingLessonService;
    private PaymentService paymentService;

    @Autowired
    public CourseReportService(CourseService courseService,
                               TheoryLessonsService theoryLessonsService,
                               DrivingLessonService drivingLessonService,
                               PaymentService paymentService) {
        this.courseService = courseService;
        this.theoryLessonsService = theoryLessonsService;
        this.drivingLessonService = drivingLessonService;
        this.paymentService = paymentService;
    }

    /**
     * Pobranie raportu dla aktywnego kursu
     * dla kursanta o podanym adresie email.
     *
     * @param email adres email kursanta
     * @return raport dla aktywnego kursu
     */
    public Optional<CourseReport> getReportByEmail(String email) {
        return courseService.getActiveCourseByEmail(email)
                .map(this::getReportByCourse);
    }

    /**
     * Pobranie raportu dla podanego kursu.
     *
     * @param course kurs
     * @return raport dla kursu
     */
    public CourseReport getReportByCourse(Course course) {
        CourseReport report = new CourseReport();
        setReportedDataByLicenseCategory(report, course.getLicenseCategory());
        setReportedDataByCourse(report, course);
        setReportedComment(report);
        return report;
    }

    /**
     * Pobranie do podanego raportu danych
     * na podstawie podanej kategorii prawa jazdy.
     *
     * @param report   raport do edycji
     * @param category kategoria prawa jazdy
     */
    private void setReportedDataByLicenseCategory(CourseReport report, LicenceCategory category) {
        if (category != null) {
            report.setCategory(category);
            report.setRequiredAge(category.requiredAge);
            report.setPrice(category.price);
            report.setTheoryHours(Constants.REQUIRED_THEORY_HOURS);
            report.setPracticeHours(category.practiceHours);
        }
    }

    /**
     * Pobranie do podanego raportu danych
     * na podstawie podanego kursu.
     *
     * @param report raport do edycji
     * @param course kurs
     */
    private void setReportedDataByCourse(CourseReport report, Course course) {
        if (course != null) {
            report.setCourseStatus(course.getCourseStatus());
            report.setPassedCoursePercent(calculatePassedCoursePercent(course));
            report.setPassedTheoryHours(getPassedTheoryHours(course));
            report.setPassedPracticeHours(getPassedPracticeHours(course));
            report.setPaymentStatus(getReportedPaymentStatus(course));
            report.setExtraDrivingLessonsHours(calculateExtraDrivingLessonsHours(course));
            report.setStartDate(course.getStartDate());
            report.setEndTime(course.getEndDate());
        }
    }

    /**
     * Obliczenie stopnia ukończenia podanego kursu
     * (w procentach)
     *
     * @param course kurs
     * @return obliczona wartość z zakresu: [0, 100]
     */
    private Integer calculatePassedCoursePercent(Course course) {
        int percent = 0;
        CourseStatus status = course.getCourseStatus();
        switch (status) {
            case DOCUMENTS_SUBMISSION:
                percent = 5;
                break;
            case LECTURES:
                Float theoryPassed = Float.valueOf(getPassedTheoryHours(course));
                Float theoryRequired = Float.valueOf(Constants.REQUIRED_THEORY_HOURS);
                float calculatedTheory = Math.min(theoryPassed / theoryRequired, 100);
                int theoryPercent = Math.round(10 + 30 * calculatedTheory);
                percent = Math.min(theoryPercent, 40);
                break;
            case THEORY_INTERNAL_EXAM:
                percent = 40;
                break;
            case DRIVING_LESSONS:
                Float practicePassed = Float.valueOf(getPassedPracticeHours(course));
                Float practiceRequired = Float.valueOf(course.getLicenseCategory().practiceHours);
                float calculatedPractice = Math.min(practicePassed / practiceRequired, 100);
                int practicePercent = Math.round(50 + 30 * calculatedPractice);
                percent = Math.min(practicePercent, 80);
                break;
            case PRACTICAL_INTERNAL_EXAM:
                percent = 80;
                break;
            case STATE_EXAMS:
                percent = 90;
                break;
            case FINISHED:
                percent = 100;
                break;
        }
        return percent;
    }

    /**
     * Obliczenie liczby ukończonych godzin w części teoretycznej.
     *
     * @param course kurs
     * @return liczba zaliczonych wykładów
     */
    private Integer getPassedTheoryHours(Course course) {
        return theoryLessonsService.getCurrentlyPassedHoursOfTheoryLessonsByCourse(course);
    }

    /**
     * Obliczenie liczby ukończonych godzin w części praktycznej.
     *
     * @param course kurs
     * @return liczba zaliczonych jazd szkoleniowych
     */
    private Integer getPassedPracticeHours(Course course) {
        return drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
    }

    /**
     * Pobranie do podanego raportu statusu rozliczenia
     * płatności na podstawie podanego kursu.
     *
     * @param course kurs
     */
    private ProcessingStatus getReportedPaymentStatus(Course course) {
        Boolean isCompleted = paymentService.checkIfAllPaymentsCompleted(course);
        if (isCompleted) {
            return ProcessingStatus.COMPLETED;
        }
        return ProcessingStatus.TO_COMPLETE;
    }

    /**
     * Obliczenie liczby godzin dodatkowych jazd
     * szkoleniowych dla podanego kursu.
     *
     * @param course kurs
     * @return obliczona liczba godzin
     */
    private Integer calculateExtraDrivingLessonsHours(Course course) {
        Integer passedHours = drivingLessonService
                .getAllPassedHoursOfDrivingLessonsByCourse(course);
        Integer courseHours = course.getLicenseCategory().practiceHours;
        int extraHours = passedHours - courseHours;
        return Math.max(extraHours, 0);
    }

    /**
     * Pobranie do podanego raportu komentarza
     * na podstawie stopnia ukończenia kursu.
     *
     * @param report raport do edycji
     */
    private void setReportedComment(CourseReport report) {
        String comment;
        Integer passedCoursePercent = report.getPassedCoursePercent();
        if (passedCoursePercent < 25) {
            comment = Messages.FIRST_REPORT_COMMENT;
        } else if (passedCoursePercent < 50) {
            comment = Messages.SECOND_REPORT_COMMENT;
        } else if (passedCoursePercent < 75) {
            comment = Messages.THIRD_REPORT_COMMENT;
        } else if (passedCoursePercent < 100) {
            comment = Messages.FOURTH_REPORT_COMMENT;
        } else {
            comment = Messages.FIFTH_REPORT_COMMENT;
        }
        report.setComment(comment);
    }

}
