package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.TheoryLessonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla zajęć teoretycznych odbywanych w ramach kursu nauki jazdy
 */
@Service
public class TheoryLessonsService {

    private TheoryLessonsRepository theoryLessonsRepository;

    @Autowired
    public TheoryLessonsService(TheoryLessonsRepository theoryLessonsRepository) {
        this.theoryLessonsRepository = theoryLessonsRepository;
    }

}
