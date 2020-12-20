package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.DrivingLesson;
import com.project.webapp.drivingschool.data.service.DrivingLessonService;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Kontroler dla zajęć praktycznych (jazd szkoleniowych)
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/driving")
public class DrivingLessonController {

    private DrivingLessonService drivingLessonService;

    @Autowired
    public DrivingLessonController(DrivingLessonService drivingLessonService) {
        this.drivingLessonService = drivingLessonService;
    }

    @GetMapping("/all/byEmail")
    @ResponseBody
    public Set<DrivingLesson> getAllDrivingLessonsByStudentEmail(@RequestParam("email") String email) {
        return drivingLessonService.getAllDrivingLessonsByEmail(email);
    }

    @GetMapping("/all/byEmployee")
    @ResponseBody
    public Set<DrivingLesson> getAllDrivingLessonsByEmployeeEmail(@RequestParam("email") String email) {
        return drivingLessonService.getAllDrivingLessonsByEmployeeEmail(email);
    }

    @GetMapping("/hours/passed/byCourse")
    @ResponseBody
    public Integer getAllPassedHoursOfDrivingLessonsByCourse(@RequestBody Course course) {
        return drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
    }

    @GetMapping("/isPassed/byCourse")
    @ResponseBody
    public Boolean isDrivingLessonsPassedByCourse(@RequestBody Course course) {
        return drivingLessonService.isDrivingLessonsPassedByCourse(course);
    }

    @GetMapping("/course")
    @ResponseBody
    public Optional<Course> findCourseByDrivingLessonId(@RequestParam("id") Long id) {
        return drivingLessonService.findCourseByDrivingLessonId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<DrivingLesson> addDrivingLesson(@RequestBody DrivingLesson lesson,
                                                          @RequestParam("student") String studentEmail,
                                                          @RequestParam("employee") String employeeEmail) {
        return drivingLessonService.addDrivingLesson(lesson, studentEmail, employeeEmail);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<DrivingLesson> changeLessonStatusByDrivingLessonId(@RequestParam("id") Long id,
                                                                             @RequestBody LessonStatus status) {
        return drivingLessonService.changeLessonStatusByDrivingLessonId(id, status);
    }

}
