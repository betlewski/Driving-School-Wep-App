package com.project.webapp.drivingschool.data.utils;

import com.project.webapp.drivingschool.data.model.InternalExam;
import com.project.webapp.drivingschool.data.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model danych przesyłany w REST API.
 * Zawiera egzamin wewnętrzny i przypisanego do niego kursanta.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalExamRest {

    /**
     * Kursant odbywający egzamin.
     */
    private Student student;

    /**
     * Egzamin wewnętrzny.
     */
    private InternalExam internalExam;

}
