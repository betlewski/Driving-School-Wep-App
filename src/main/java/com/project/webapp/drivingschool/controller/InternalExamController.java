package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.InternalExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla egzaminów wewnętrznych w trakcie kursu
 */
@CrossOrigin
@RestController
@RequestMapping("/api/exam")
public class InternalExamController {

    private InternalExamService internalExamService;

    @Autowired
    public InternalExamController(InternalExamService internalExamService) {
        this.internalExamService = internalExamService;
    }

}
