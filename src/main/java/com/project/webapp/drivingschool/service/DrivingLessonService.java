package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.DrivingLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla zajęć praktycznych (jazd szkoleniowych)
 */
@Service
public class DrivingLessonService {

    private DrivingLessonRepository drivingLessonRepository;

    @Autowired
    public DrivingLessonService(DrivingLessonRepository drivingLessonRepository) {
        this.drivingLessonRepository = drivingLessonRepository;
    }

}
