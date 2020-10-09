package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla pojedynczych wykładów w ramach cyklu wykładów
 */
@Service
public class LectureService {

    private LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

}
