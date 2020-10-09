package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serwis dla dokumentów wymaganych w trakcie kursu
 */
@Service
public class DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

}
