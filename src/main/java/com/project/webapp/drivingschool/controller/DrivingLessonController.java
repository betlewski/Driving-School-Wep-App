package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.DrivingLesson;
import com.project.webapp.drivingschool.service.DrivingLessonService;
import com.project.webapp.drivingschool.utils.LessonStatus;
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
@RequestMapping("/api/driving")
public class DrivingLessonController {

    private DrivingLessonService drivingLessonService;

    @Autowired
    public DrivingLessonController(DrivingLessonService drivingLessonService) {
        this.drivingLessonService = drivingLessonService;
    }

    @GetMapping("/all/studentId")
    @ResponseBody
    public Set<DrivingLesson> getAllDrivingLessonsByStudentId(@RequestParam("id") Long id) {
        return drivingLessonService.getAllDrivingLessonsByStudentId(id);
    }

    @GetMapping("/all/employeeId")
    @ResponseBody
    public Set<DrivingLesson> getAllDrivingLessonsByEmployeeId(@RequestParam("id") Long id) {
        return drivingLessonService.getAllDrivingLessonsByEmployeeId(id);
    }

    @GetMapping("/passedHours")
    @ResponseBody
    public Integer getAllPassedHoursOfDrivingLessonsByCourse(@RequestBody Course course) {
        return drivingLessonService.getAllPassedHoursOfDrivingLessonsByCourse(course);
    }

    @GetMapping("/isPassed")
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
                                                          @RequestParam("id") Long id) {
        return drivingLessonService.addDrivingLesson(lesson, id);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<DrivingLesson> changeLessonStatusByDrivingLessonId(@RequestParam("id") Long id,
                                                                             @RequestBody LessonStatus status) {
        return drivingLessonService.changeLessonStatusByDrivingLessonId(id, status);
    }

}
