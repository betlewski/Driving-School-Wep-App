package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.report.CourseReport;
import com.project.webapp.drivingschool.utils.Constants;
import com.project.webapp.drivingschool.utils.CourseStatus;
import com.project.webapp.drivingschool.utils.LicenceCategory;
import com.project.webapp.drivingschool.utils.ProcessingStatus;
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
     * Komentarze pobierane podczas generowania raportu.
     */
    private static final String FIRST_COMMENT = "Przed Tobą długa droga, ale wierzymy, że sobie poradzisz. Powodzenia!";
    private static final String SECOND_COMMENT = "Dobrze Ci idzie, ale czeka Cię jeszcze trochę pracy. Nie poddawaj się!";
    private static final String THIRD_COMMENT = "Jesteś coraz bliżej celu. Pracuj dalej, a na pewno osiągniesz sukces!";
    private static final String FOURTH_COMMENT = "Ostatnia prosta. Już niedługo zostaniesz pełnoprawnym uczestnikiem ruchu drogowego!";
    private static final String FIFTH_COMMENT = "Ogromnie Ci gratulujemy i dziękujemy, że mogliśmy Ci towarzyszyć w drodze do sukcesu!";

    /**
     * Pobranie raportu dla aktywnego kursu
     * dla kursanta o podanym ID.
     *
     * @param id ID kursanta
     * @return raport dla aktywnego kursu
     */
    public Optional<CourseReport> getReportByStudentId(Long id) {
        return courseService.getActiveCourseByStudentId(id)
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
            report.setPassedTheoryPercent(calculatePassedTheoryPercent(course));
            report.setPassedPracticePercent(calculatePassedPracticePercent(course));
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
                float calculatedTheory = Float.valueOf(
                        calculatePassedTheoryPercent(course)) / 100;
                int theoryPercent = Math.round(10 + 30 * calculatedTheory);
                percent = Math.min(theoryPercent, 40);
                break;
            case THEORY_INTERNAL_EXAM:
                percent = 40;
                break;
            case DRIVING_LESSONS:
                float calculatedPractice = Float.valueOf(
                        calculatePassedPracticePercent(course)) / 100;
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
     * Obliczenie stopnia ukończenia zajęć teoretycznych
     * dla podanego kursu (w procentach)
     *
     * @param course kurs
     * @return obliczona wartość z zakresu: [0, 100]
     */
    private Integer calculatePassedTheoryPercent(Course course) {
        Float theoryPassed = Float.valueOf(theoryLessonsService
                .getCurrentlyPassedHoursOfTheoryLessonsByCourse(course));
        Float theoryRequired = Float.valueOf(Constants.REQUIRED_THEORY_HOURS);
        int percent = Math.round((theoryPassed / theoryRequired) * 100);
        return Math.min(percent, 100);
    }

    /**
     * Obliczenie stopnia ukończenia zajęć praktycznych
     * dla podanego kursu (w procentach)
     *
     * @param course kurs
     * @return obliczona wartość z zakresu: [0, 100]
     */
    private Integer calculatePassedPracticePercent(Course course) {
        Float practicePassed = Float.valueOf(drivingLessonService
                .getAllPassedHoursOfDrivingLessonsByCourse(course));
        Float practiceRequired = Float.valueOf(course.getLicenseCategory().practiceHours);
        int percent = Math.round((practicePassed / practiceRequired) * 100);
        return Math.min(percent, 100);
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
            comment = FIRST_COMMENT;
        } else if (passedCoursePercent < 50) {
            comment = SECOND_COMMENT;
        } else if (passedCoursePercent < 75) {
            comment = THIRD_COMMENT;
        } else if (passedCoursePercent < 100) {
            comment = FOURTH_COMMENT;
        } else {
            comment = FIFTH_COMMENT;
        }
        report.setComment(comment);
    }

}
