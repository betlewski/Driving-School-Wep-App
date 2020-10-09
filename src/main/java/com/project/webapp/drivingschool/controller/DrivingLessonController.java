package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.DrivingLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla zajęć praktycznych (jazd szkoleniowych)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/driving")
public class DrivingLessonController {

    private DrivingLessonService drivingLessonService;

    @Autowired
    public DrivingLessonController(DrivingLessonService drivingLessonService) {
        this.drivingLessonService = drivingLessonService;
    }

}
