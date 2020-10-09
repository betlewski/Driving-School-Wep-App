package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla kursu nauki jazdy
 */
@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

}
