package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.Lecture;
import com.project.webapp.drivingschool.model.TheoryLessons;
import com.project.webapp.drivingschool.service.TheoryLessonsService;
import com.project.webapp.drivingschool.utils.LessonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * Kontroler dla zajęć teoretycznych odbywanych w ramach kursu nauki jazdy
 */
@CrossOrigin
@RestController
@RequestMapping("/api/theory")
public class TheoryLessonsController {

    private TheoryLessonsService theoryLessonsService;

    @Autowired
    public TheoryLessonsController(TheoryLessonsService theoryLessonsService) {
        this.theoryLessonsService = theoryLessonsService;
    }

    @GetMapping("/all/studentId")
    @ResponseBody
    public Set<TheoryLessons> getAllTheoryLessonsByStudentId(@RequestParam("id") Long id) {
        return theoryLessonsService.getAllTheoryLessonsByStudentId(id);
    }

    @GetMapping("/all/studentIdAndStatus")
    @ResponseBody
    public Set<TheoryLessons> getTheoryLessonsByStudentIdAndLessonStatus(@RequestParam("id") Long id,
                                                                         @RequestParam("status") LessonStatus status) {
        return theoryLessonsService.getTheoryLessonsByStudentIdAndLessonStatus(id, status);
    }

    @GetMapping("/allLectures/studentId")
    @ResponseBody
    public Set<Lecture> getAllLecturesForAcceptedTheoryLessonsByStudentId(@RequestParam("id") Long id) {
        return theoryLessonsService.getAllLecturesForAcceptedTheoryLessonsByStudentId(id);
    }

    @GetMapping("/passedHours")
    @ResponseBody
    public Integer getCurrentlyPassedHoursOfTheoryLessonsByCourse(@RequestBody Course course) {
        return theoryLessonsService.getCurrentlyPassedHoursOfTheoryLessonsByCourse(course);
    }

    @GetMapping("/all/employeeId")
    @ResponseBody
    public Set<TheoryLessons> getAllTheoryLessonsByEmployeeId(@RequestParam("id") Long id) {
        return theoryLessonsService.getAllTheoryLessonsByEmployeeId(id);
    }

    @GetMapping("/course")
    @ResponseBody
    public Optional<Course> findCourseByTheoryLessonsId(@RequestParam("id") Long id) {
        return theoryLessonsService.findCourseByTheoryLessonsId(id);
    }

    @GetMapping("/isPassed/course")
    @ResponseBody
    public Boolean isTheoryLessonsPassedByCourse(@RequestBody Course course) {
        return theoryLessonsService.isTheoryLessonsPassedByCourse(course);
    }

    @GetMapping("/isPassed/studentId")
    @ResponseBody
    public Boolean isTheoryLessonsActiveByStudentId(@RequestParam("id") Long id) {
        return theoryLessonsService.isTheoryLessonsActiveByStudentId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<TheoryLessons> addTheoryLessons(@RequestParam("id") Long studentId,
                                                          @RequestBody Long lectureSeriesId) {
        return theoryLessonsService.addTheoryLessons(studentId, lectureSeriesId);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<TheoryLessons> changeLessonStatusByTheoryLessonsId(@RequestParam("id") Long id,
                                                                             @RequestBody LessonStatus status) {
        return theoryLessonsService.changeLessonStatusByTheoryLessonsId(id, status);
    }

}
