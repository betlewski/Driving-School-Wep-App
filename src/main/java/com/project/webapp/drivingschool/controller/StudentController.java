package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla kursantów (klientów szkoły nauki jazdy)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

}
