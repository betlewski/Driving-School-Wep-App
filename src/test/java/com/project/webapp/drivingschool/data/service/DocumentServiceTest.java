package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Document;
import com.project.webapp.drivingschool.data.utils.DocumentType;
import com.project.webapp.drivingschool.data.utils.ProcessingStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

/**
 * Testy jednostkowe serwisu dla dokumentów wymaganych w trakcie kursu.
 */
public class DocumentServiceTest {

    private static final DocumentService documentService =
            new DocumentService(null, null, null, null);

    @Test
    public void test_checkIfMedicalExamsCompleted_emptyDocuments() {
        Course course = new Course();
        course.setDocuments(new HashSet<>());
        Boolean actual = documentService.checkIfMedicalExamsCompleted(course);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_checkIfMedicalExamsCompleted_noMedicalExams() {
        Document parentPermission = new Document();
        parentPermission.setDocumentType(DocumentType.PARENT_PERMISSION);
        Course course = new Course();
        course.setDocuments(new HashSet<>(Collections.singletonList(parentPermission)));
        Boolean actual = documentService.checkIfMedicalExamsCompleted(course);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_checkIfMedicalExamsCompleted_notCompleted() {
        Document medicalExams = new Document();
        medicalExams.setDocumentType(DocumentType.MEDICAL_EXAMS);
        medicalExams.setProcessingStatus(ProcessingStatus.REQUESTED);
        Course course = new Course();
        course.setDocuments(new HashSet<>(Collections.singletonList(medicalExams)));
        Boolean actual = documentService.checkIfMedicalExamsCompleted(course);
        Assert.assertFalse(actual);
    }

    @Test
    public void test_checkIfMedicalExamsCompleted_completed() {
        Document medicalExams = new Document();
        medicalExams.setDocumentType(DocumentType.MEDICAL_EXAMS);
        medicalExams.setProcessingStatus(ProcessingStatus.COMPLETED);
        Course course = new Course();
        course.setDocuments(new HashSet<>(Collections.singletonList(medicalExams)));
        Boolean actual = documentService.checkIfMedicalExamsCompleted(course);
        Assert.assertTrue(actual);
    }

}
