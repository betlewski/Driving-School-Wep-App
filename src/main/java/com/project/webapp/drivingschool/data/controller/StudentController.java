package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Kontroler dla kursantów (klientów szkoły nauki jazdy)
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/byEmail")
    @ResponseBody
    public Student getStudentByEmail(@RequestParam("email") String email) {
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/byCourse")
    @ResponseBody
    public Optional<Student> findStudentByCourse(@RequestBody Course course) {
        return studentService.findStudentByCourse(course);
    }

    @GetMapping("/email/exist")
    @ResponseBody
    public Boolean emailExisting(@RequestParam("email") String email) {
        return studentService.emailExisting(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/edit")
    public ResponseEntity<Student> editStudent(@RequestParam("email") String email,
                                               @RequestBody Student newStudent) {
        return studentService.editStudent(email, newStudent);
    }

    @PutMapping("/edit/full")
    public ResponseEntity<Student> editStudentFull(@RequestParam("email") String email,
                                                   @RequestBody Student newStudent) {
        return studentService.editStudentFull(email, newStudent);
    }

}
