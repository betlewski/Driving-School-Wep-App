package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla pojedynczych wykładów w ramach cyklu wykładów
 */
@CrossOrigin
@RestController
@RequestMapping("/api/lecture")
public class LectureController {

    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

}
