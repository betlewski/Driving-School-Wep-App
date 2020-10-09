package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler dla dokumentów wymaganych w trakcie kursu
 */
@CrossOrigin
@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

}
