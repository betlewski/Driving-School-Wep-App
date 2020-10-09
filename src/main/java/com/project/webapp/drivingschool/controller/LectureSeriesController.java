package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.LectureSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla cyklu wykładów w szkole jazdy
 */
@CrossOrigin
@RestController
@RequestMapping("/api/series")
public class LectureSeriesController {

    private LectureSeriesService lectureSeriesService;

    @Autowired
    public LectureSeriesController(LectureSeriesService lectureSeriesService) {
        this.lectureSeriesService = lectureSeriesService;
    }

}
