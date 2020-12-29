package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Lecture;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Testy jednostkowe serwisu dla pojedynczych wykładów w ramach cyklu wykładów.
 */
public class LectureServiceTest {

    private static final LectureService lectureService = new LectureService(
            null, null, null, null);

    @Test
    public void test_checkIfSumEqualsRequiredTheoryHoursForLecturesSet_notEquals() {
        Lecture lecture1 = new Lecture();
        lecture1.setStartTime(LocalDateTime.of(2020, 11, 1, 8, 0));
        lecture1.setEndTime(LocalDateTime.of(2020, 11, 1, 18, 0));
        Lecture lecture2 = new Lecture();
        lecture2.setStartTime(LocalDateTime.of(2020, 11, 2, 8, 0));
        lecture2.setEndTime(LocalDateTime.of(2020, 11, 2, 18, 0));
        Lecture lecture3 = new Lecture();
        lecture3.setStartTime(LocalDateTime.of(2020, 11, 3, 8, 0));
        lecture3.setEndTime(LocalDateTime.of(2020, 11, 3, 10, 0));
        Set<Lecture> lectureSet = new HashSet<>(Arrays.asList(lecture1, lecture2, lecture3));
        Boolean actual = lectureService.checkIfSumEqualsRequiredTheoryHoursForLecturesSet(lectureSet);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_checkIfSumEqualsRequiredTheoryHoursForLecturesSet_equals() {
        Lecture lecture1 = new Lecture();
        lecture1.setStartTime(LocalDateTime.of(2020, 11, 1, 8, 0));
        lecture1.setEndTime(LocalDateTime.of(2020, 11, 1, 18, 0));
        Lecture lecture2 = new Lecture();
        lecture2.setStartTime(LocalDateTime.of(2020, 11, 2, 8, 0));
        lecture2.setEndTime(LocalDateTime.of(2020, 11, 2, 18, 0));
        Lecture lecture3 = new Lecture();
        lecture3.setStartTime(LocalDateTime.of(2020, 11, 3, 8, 0));
        lecture3.setEndTime(LocalDateTime.of(2020, 11, 3, 18, 0));
        Set<Lecture> lectureSet = new HashSet<>(Arrays.asList(lecture1, lecture2, lecture3));
        Boolean actual = lectureService.checkIfSumEqualsRequiredTheoryHoursForLecturesSet(lectureSet);
        Assert.assertTrue(actual);
    }

}
