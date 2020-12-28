package com.project.webapp.drivingschool.data.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Testy jednostkowe narzędzi dla danych.
 */
public class DataUtilsTest {

    @Test
    public void test_isPasswordCorrect_null() {
        Assert.assertFalse(DataUtils.isPasswordCorrect(null));
    }

    @Test
    public void test_isPasswordCorrect_shortLength() {
        String password = "Pwd1";
        Assert.assertFalse(DataUtils.isPasswordCorrect(password));
    }

    @Test
    public void test_isPasswordCorrect_noDigit() {
        String password = "Password";
        Assert.assertFalse(DataUtils.isPasswordCorrect(password));
    }

    @Test
    public void test_isPasswordCorrect_noLowerCase() {
        String password = "PASSWORD1";
        Assert.assertFalse(DataUtils.isPasswordCorrect(password));
    }

    @Test
    public void test_isPasswordCorrect_noCapitalLetter() {
        String password = "password1";
        Assert.assertFalse(DataUtils.isPasswordCorrect(password));
    }

    @Test
    public void test_isPasswordCorrect_whiteChar() {
        String password = "Password 1";
        Assert.assertFalse(DataUtils.isPasswordCorrect(password));
    }

    @Test
    public void test_isPasswordCorrect_correct() {
        String password = "Password1";
        Assert.assertTrue(DataUtils.isPasswordCorrect(password));
    }

    @Test
    public void test_isStartAndEndTimeCorrect_null() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, 0, 0);
        Assert.assertFalse(DataUtils.isStartAndEndTimeCorrect(startTime, null));
    }

    @Test
    public void test_isStartAndEndTimeCorrect_endBeforeStart() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 10, 31, 0, 0);
        Assert.assertFalse(DataUtils.isStartAndEndTimeCorrect(startTime, endTime));
    }

    @Test
    public void test_isStartAndEndTimeCorrect_bothNotCorrect() {
        int startHour = Constants.START_WORK_HOUR - 1;
        int endHour = Constants.END_WORK_HOUR + 1;
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, startHour, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 1, endHour, 0);
        Assert.assertFalse(DataUtils.isStartAndEndTimeCorrect(startTime, endTime));
    }

    @Test
    public void test_isStartAndEndTimeCorrect_startNotCorrect() {
        int startHour = Constants.START_WORK_HOUR - 1;
        int endHour = Constants.END_WORK_HOUR - 1;
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, startHour, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 1, endHour, 0);
        Assert.assertFalse(DataUtils.isStartAndEndTimeCorrect(startTime, endTime));
    }

    @Test
    public void test_isStartAndEndTimeCorrect_endNotCorrect() {
        int startHour = Constants.START_WORK_HOUR + 1;
        int endHour = Constants.END_WORK_HOUR + 1;
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, startHour, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 1, endHour, 0);
        Assert.assertFalse(DataUtils.isStartAndEndTimeCorrect(startTime, endTime));
    }

    @Test
    public void test_isStartAndEndTimeCorrect_correct() {
        int startHour = Constants.START_WORK_HOUR + 1;
        int endHour = Constants.END_WORK_HOUR - 1;
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, startHour, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 11, 1, endHour, 0);
        Assert.assertTrue(DataUtils.isStartAndEndTimeCorrect(startTime, endTime));
    }

}
