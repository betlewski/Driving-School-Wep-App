package com.project.webapp.drivingschool.security.utils;

import com.project.webapp.drivingschool.data.model.Employee;
import com.project.webapp.drivingschool.data.model.Student;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testy jednostkowe klasy do zarządzania tokenami JWT.
 */
public class JwtTokenUtilTest {

    private static final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("test-secret-key");

    private String generateToken() {
        Student student = new Student();
        student.setEmail("student@test.pl");
        return jwtTokenUtil.generateToken(student);
    }

    @Test
    public void test_getUsernameFromToken() {
        String token = generateToken();
        String expected = "student@test.pl";
        String actual = jwtTokenUtil.getUsernameFromToken(token);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_isTokenExpired() {
        String token = generateToken();
        Assert.assertFalse(jwtTokenUtil.isTokenExpired(token));
    }

    @Test
    public void test_validateToken_invalid() {
        String token = generateToken();
        Employee employee = new Employee();
        employee.setEmail("employee@test.pl");
        Assert.assertFalse(jwtTokenUtil.validateToken(token, employee));
    }

    @Test
    public void test_validateToken_valid() {
        String token = generateToken();
        Student student = new Student();
        student.setEmail("student@test.pl");
        Assert.assertTrue(jwtTokenUtil.validateToken(token, student));
    }

}
