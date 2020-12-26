package com.project.webapp.drivingschool.data.utils;

import com.project.webapp.drivingschool.data.model.TheoryLessons;
import com.project.webapp.drivingschool.data.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model danych przesyłany w REST API.
 * Zawiera zajęcia teoretyczne i przypisanego do nich kursanta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheoryLessonsRest {

    /**
     * Kursant odbywający zajęcia.
     */
    private Student student;

    /**
     * Zajęcia teoretyczne.
     */
    private TheoryLessons theoryLessons;

}
