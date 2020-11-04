package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Document;
import com.project.webapp.drivingschool.data.service.DocumentService;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Kontroler dla dokumentów wymaganych w trakcie kursu
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/document")
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/all/byStudentId")
    @ResponseBody
    public Set<Document> getAllDocumentsForActiveCourseByStudentId(@RequestParam("id") Long id) {
        return documentService.getAllDocumentsForActiveCourseByStudentId(id);
    }

    @GetMapping("/all/byStudentId/byStatus")
    @ResponseBody
    public Set<Document> getDocumentsForActiveCourseByStudentIdAndProcessingStatus(@RequestParam("id") Long id,
                                                                                   @RequestParam("status") ProcessingStatus status) {
        return documentService.getDocumentsForActiveCourseByStudentIdAndProcessingStatus(id, status);
    }

    @GetMapping("/course")
    @ResponseBody
    public Optional<Course> findCourseByDocumentId(@RequestParam("id") Long id) {
        return documentService.findCourseByDocumentId(id);
    }

    @GetMapping("/isCompleted/medicalExams/byCourse")
    @ResponseBody
    public Boolean checkIfMedicalExamsCompleted(@RequestBody Course course) {
        return documentService.checkIfMedicalExamsCompleted(course);
    }

    @GetMapping("/isCompleted/all/byCourse")
    @ResponseBody
    public Boolean checkIfAllDocumentsCompleted(@RequestBody Course course) {
        return documentService.checkIfAllDocumentsCompleted(course);
    }

    @PostMapping("/add")
    public ResponseEntity<Document> addDocument(@RequestBody Document document,
                                                @RequestParam("id") Long id) {
        return documentService.addDocument(document, id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteDocument(@RequestParam("id") Long id) {
        return documentService.deleteDocument(id);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<Document> changeProcessingStatusByDocumentId(@RequestParam("id") Long id,
                                                                       @RequestBody ProcessingStatus status) {
        return documentService.changeProcessingStatusByDocumentId(id, status);
    }

    @PutMapping("/edit/request")
    public ResponseEntity<Document> requestDocumentByDocumentId(@RequestBody Long id) {
        return documentService.requestDocumentByDocumentId(id);
    }

}
