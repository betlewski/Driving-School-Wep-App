package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.LectureSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla cyklu wykładów w szkole jazdy
 */
@Service
public class LectureSeriesService {

    private LectureSeriesRepository lectureSeriesRepository;

    @Autowired
    public LectureSeriesService(LectureSeriesRepository lectureSeriesRepository) {
        this.lectureSeriesRepository = lectureSeriesRepository;
    }

}
