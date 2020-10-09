package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.InternalExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla egzaminów wewnętrznych w trakcie kursu
 */
@Service
public class InternalExamService {

    private InternalExamRepository internalExamRepository;

    @Autowired
    public InternalExamService(InternalExamRepository internalExamRepository) {
        this.internalExamRepository = internalExamRepository;
    }

}
