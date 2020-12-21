package com.project.webapp.drivingschool.data.utils;

import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.model.DrivingLesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model danych przesyłany w REST API.
 * Zawiera jazdę szkoleniową i przypisanego do niej kursanta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrivingLessonRest {

    /**
     * Kursant odbywający jazdę.
     */
    private Student student;

    /**
     * Jazda szkoleniowa.
     */
    private DrivingLesson drivingLesson;

}
