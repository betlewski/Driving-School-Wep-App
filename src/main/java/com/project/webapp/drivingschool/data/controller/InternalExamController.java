package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.InternalExam;
import com.project.webapp.drivingschool.data.service.InternalExamService;
import com.project.webapp.drivingschool.data.utils.ExamType;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Kontroler dla egzaminów wewnętrznych w trakcie kursu
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/exam")
public class InternalExamController {

    private InternalExamService internalExamService;

    @Autowired
    public InternalExamController(InternalExamService internalExamService) {
        this.internalExamService = internalExamService;
    }

    @GetMapping("/all/byStudent")
    @ResponseBody
    public Set<InternalExam> getAllInternalExamsByStudentEmail(@RequestParam("email") String email) {
        return internalExamService.getAllInternalExamsByEmail(email);
    }

    @GetMapping("/all/byStudent/byType")
    @ResponseBody
    public Set<InternalExam> getAllInternalExamsByStudentEmailAndExamType(@RequestParam("email") String email,
                                                                          @RequestParam("type") ExamType type) {
        return internalExamService.getAllInternalExamsByEmailAndExamType(email, type);
    }

    @GetMapping("/all/byEmployee")
    @ResponseBody
    public Set<InternalExam> getAllInternalExamsByEmployeeEmail(@RequestParam("email") String email) {
        return internalExamService.getAllInternalExamsByEmployeeEmail(email);
    }

    @GetMapping("/course")
    @ResponseBody
    public Optional<Course> findCourseByInternalExamId(@RequestParam("id") Long id) {
        return internalExamService.findCourseByInternalExamId(id);
    }

    @GetMapping("/isPassed/byCourse/byType")
    @ResponseBody
    public Boolean isInternalExamPassedByCourseAndExamType(@RequestBody Course course,
                                                           @RequestParam("type") ExamType type) {
        return internalExamService.isInternalExamPassedByCourseAndExamType(course, type);
    }

    @PostMapping("/add")
    public ResponseEntity<InternalExam> addExam(@RequestBody InternalExam exam,
                                                @RequestParam("student") String studentEmail,
                                                @RequestParam("employee") String employeeEmail) {
        return internalExamService.addExam(exam, studentEmail, employeeEmail);
    }

    @PutMapping("/edit")
    public ResponseEntity<InternalExam> editExam(@RequestParam("id") Long id,
                                                 @RequestBody InternalExam newExam) {
        return internalExamService.editExam(id, newExam);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<InternalExam> changeLessonStatusByExamId(@RequestParam("id") Long id,
                                                                   @RequestBody LessonStatus status) {
        return internalExamService.changeLessonStatusByExamId(id, status);
    }

}

