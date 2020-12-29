package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.DrivingLesson;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
import com.project.webapp.drivingschool.data.utils.LicenceCategory;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Testy jednostkowe serwisu dla zajęć praktycznych (jazd szkoleniowych).
 */
public class DrivingLessonServiceTest {

    private static final DrivingLessonService drivingLessonService = new DrivingLessonService(
            null, null, null, null, null, null, null);

    @Test
    public void test_getAllPassedHoursOfDrivingLessonsByCourse_emptyLessons() {
        Course course = new Course();
        course.setDrivingLessons(new HashSet<>());
        Integer expected = 0;
        Integer actual = drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getAllPassedHoursOfDrivingLessonsByCourse_noPassed() {
        DrivingLesson lesson = new DrivingLesson();
        lesson.setLessonStatus(LessonStatus.REQUESTED);
        lesson.setStartTime(LocalDateTime.of(2020, 11, 1, 10, 0));
        lesson.setEndTime(LocalDateTime.of(2020, 11, 1, 12, 0));
        Course course = new Course();
        course.setDrivingLessons(new HashSet<>(Collections.singletonList(lesson)));
        Integer expected = 0;
        Integer actual = drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getAllPassedHoursOfDrivingLessonsByCourse_onePassed() {
        DrivingLesson lesson = new DrivingLesson();
        lesson.setLessonStatus(LessonStatus.PASSED);
        lesson.setStartTime(LocalDateTime.of(2020, 11, 1, 10, 0));
        lesson.setEndTime(LocalDateTime.of(2020, 11, 1, 12, 0));
        Course course = new Course();
        course.setDrivingLessons(new HashSet<>(Collections.singletonList(lesson)));
        Integer expected = 2;
        Integer actual = drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getAllPassedHoursOfDrivingLessonsByCourse_manyPassed() {
        DrivingLesson lesson1 = new DrivingLesson();
        lesson1.setLessonStatus(LessonStatus.PASSED);
        lesson1.setStartTime(LocalDateTime.of(2020, 11, 1, 10, 0));
        lesson1.setEndTime(LocalDateTime.of(2020, 11, 1, 12, 0));
        DrivingLesson lesson2 = new DrivingLesson();
        lesson2.setLessonStatus(LessonStatus.ACCEPTED);
        lesson2.setStartTime(LocalDateTime.of(2020, 11, 1, 13, 0));
        lesson2.setEndTime(LocalDateTime.of(2020, 11, 1, 14, 0));
        DrivingLesson lesson3 = new DrivingLesson();
        lesson3.setLessonStatus(LessonStatus.PASSED);
        lesson3.setStartTime(LocalDateTime.of(2020, 11, 1, 16, 0));
        lesson3.setEndTime(LocalDateTime.of(2020, 11, 1, 19, 0));
        Course course = new Course();
        course.setDrivingLessons(new HashSet<>(Arrays.asList(lesson1, lesson2, lesson3)));
        Integer expected = 5;
        Integer actual = drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_isDrivingLessonsPassedByCourse_notPassed() {
        DrivingLesson lesson1 = new DrivingLesson();
        lesson1.setLessonStatus(LessonStatus.PASSED);
        lesson1.setStartTime(LocalDateTime.of(2020, 11, 1, 6, 0));
        lesson1.setEndTime(LocalDateTime.of(2020, 11, 1, 11, 0));
        DrivingLesson lesson2 = new DrivingLesson();
        lesson2.setLessonStatus(LessonStatus.REQUESTED);
        lesson2.setStartTime(LocalDateTime.of(2020, 11, 1, 12, 0));
        lesson2.setEndTime(LocalDateTime.of(2020, 11, 1, 17, 0));
        Course course = new Course();
        course.setLicenseCategory(LicenceCategory.AM);
        course.setDrivingLessons(new HashSet<>(Arrays.asList(lesson1, lesson2)));
        Boolean actual = drivingLessonService.isDrivingLessonsPassedByCourse(course);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_isDrivingLessonsPassedByCourse_passed() {
        DrivingLesson lesson1 = new DrivingLesson();
        lesson1.setLessonStatus(LessonStatus.PASSED);
        lesson1.setStartTime(LocalDateTime.of(2020, 11, 1, 6, 0));
        lesson1.setEndTime(LocalDateTime.of(2020, 11, 1, 11, 0));
        DrivingLesson lesson2 = new DrivingLesson();
        lesson2.setLessonStatus(LessonStatus.PASSED);
        lesson2.setStartTime(LocalDateTime.of(2020, 11, 1, 12, 0));
        lesson2.setEndTime(LocalDateTime.of(2020, 11, 1, 17, 0));
        Course course = new Course();
        course.setLicenseCategory(LicenceCategory.AM);
        course.setDrivingLessons(new HashSet<>(Arrays.asList(lesson1, lesson2)));
        Boolean actual = drivingLessonService.isDrivingLessonsPassedByCourse(course);
        Assert.assertTrue(actual);
    }

}
