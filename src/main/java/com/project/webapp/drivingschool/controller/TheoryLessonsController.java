package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.TheoryLessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla zajęć teoretycznych odbywanych w ramach kursu nauki jazdy
 */
@CrossOrigin
@RestController
@RequestMapping("/api/theory")
public class TheoryLessonsController {

    private TheoryLessonsService theoryLessonsService;

    @Autowired
    public TheoryLessonsController(TheoryLessonsService theoryLessonsService) {
        this.theoryLessonsService = theoryLessonsService;
    }

}
