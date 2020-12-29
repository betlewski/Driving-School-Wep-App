package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.report.CourseReport;
import com.project.webapp.drivingschool.data.utils.Constants;
import com.project.webapp.drivingschool.data.utils.LicenceCategory;
import com.project.webapp.drivingschool.data.utils.Messages;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testy jednostkowe serwisu dla raportu z kursu.
 */
public class CourseReportServiceTest {

    private static final CourseReportService courseReportService =
            new CourseReportService(null, null, null, null, null);

    @Test
    public void test_setReportedDataByLicenseCategory() {
        CourseReport courseReport = new CourseReport();
        courseReportService.setReportedDataByLicenseCategory(courseReport, LicenceCategory.A);
        Assert.assertEquals(LicenceCategory.A, courseReport.getCategory());
        Assert.assertEquals(LicenceCategory.A.requiredAge, courseReport.getRequiredAge());
        Assert.assertEquals(LicenceCategory.A.price, courseReport.getPrice());
        Assert.assertEquals(Constants.REQUIRED_THEORY_HOURS, courseReport.getTheoryHours());
        Assert.assertEquals(LicenceCategory.A.practiceHours, courseReport.getPracticeHours());
    }

    @Test
    public void test_setReportedComment_halfPassed() {
        CourseReport courseReport = new CourseReport();
        courseReport.setPassedCoursePercent(50);
        courseReportService.setReportedComment(courseReport);
        String expected = Messages.THIRD_REPORT_COMMENT;
        String actual = courseReport.getComment();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_setReportedComment_fullPassed() {
        CourseReport courseReport = new CourseReport();
        courseReport.setPassedCoursePercent(100);
        courseReportService.setReportedComment(courseReport);
        String expected = Messages.FIFTH_REPORT_COMMENT;
        String actual = courseReport.getComment();
        Assert.assertEquals(expected, actual);
    }

}
