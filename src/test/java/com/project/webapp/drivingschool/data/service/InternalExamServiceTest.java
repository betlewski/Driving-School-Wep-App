package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.InternalExam;
import com.project.webapp.drivingschool.data.utils.ExamType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

/**
 * Testy jednostkowe serwisu dla egzaminów wewnętrznych w trakcie kursu.
 */
public class InternalExamServiceTest {

    private static final InternalExamService internalExamService = new InternalExamService(
            null, null, null, null, null, null, null);

    @Test
    public void test_isInternalExamPassedByCourseAndExamType_emptyExams() {
        Course course = new Course();
        course.setInternalExams(new HashSet<>());
        Boolean actual = internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_isInternalExamPassedByCourseAndExamType_passedAnother() {
        InternalExam theoryExam = new InternalExam();
        theoryExam.setExamType(ExamType.THEORETICAL);
        theoryExam.setIsPassed(true);
        Course course = new Course();
        course.setInternalExams(new HashSet<>(Collections.singletonList(theoryExam)));
        Boolean actual = internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_isInternalExamPassedByCourseAndExamType_notPassed() {
        InternalExam practicalExam = new InternalExam();
        practicalExam.setExamType(ExamType.PRACTICAL);
        practicalExam.setIsPassed(false);
        Course course = new Course();
        course.setInternalExams(new HashSet<>(Collections.singletonList(practicalExam)));
        Boolean actual = internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_isInternalExamPassedByCourseAndExamType_passed() {
        InternalExam practicalExam = new InternalExam();
        practicalExam.setExamType(ExamType.PRACTICAL);
        practicalExam.setIsPassed(true);
        Course course = new Course();
        course.setInternalExams(new HashSet<>(Collections.singletonList(practicalExam)));
        Boolean actual = internalExamService.isInternalExamPassedByCourseAndExamType(course, ExamType.PRACTICAL);
        Assert.assertTrue(actual);
    }

}
