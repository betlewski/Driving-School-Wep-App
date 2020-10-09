package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla kursantów (klientów szkoły nauki jazdy)
 */
@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

}
