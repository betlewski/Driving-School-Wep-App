package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Lecture;
import com.project.webapp.drivingschool.data.model.TheoryLessons;
import com.project.webapp.drivingschool.data.service.TheoryLessonsService;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
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
@RequestMapping("/rest/theory")
public class TheoryLessonsController {

    private TheoryLessonsService theoryLessonsService;

    @Autowired
    public TheoryLessonsController(TheoryLessonsService theoryLessonsService) {
        this.theoryLessonsService = theoryLessonsService;
    }

    @GetMapping("/all/byStudentId")
    @ResponseBody
    public Set<TheoryLessons> getAllTheoryLessonsByStudentId(@RequestParam("id") Long id) {
        return theoryLessonsService.getAllTheoryLessonsByStudentId(id);
    }

    @GetMapping("/all/byStudentId/byStatus")
    @ResponseBody
    public Set<TheoryLessons> getTheoryLessonsByStudentIdAndLessonStatus(@RequestParam("id") Long id,
                                                                         @RequestParam("status") LessonStatus status) {
        return theoryLessonsService.getTheoryLessonsByStudentIdAndLessonStatus(id, status);
    }

    @GetMapping("/lectures/all/byStudentId")
    @ResponseBody
    public Set<Lecture> getAllLecturesForAcceptedTheoryLessonsByStudentId(@RequestParam("id") Long id) {
        return theoryLessonsService.getAllLecturesForAcceptedTheoryLessonsByStudentId(id);
    }

    @GetMapping("/hours/passed/byCourse")
    @ResponseBody
    public Integer getCurrentlyPassedHoursOfTheoryLessonsByCourse(@RequestBody Course course) {
        return theoryLessonsService.getCurrentlyPassedHoursOfTheoryLessonsByCourse(course);
    }

    @GetMapping("/all/byEmployeeId")
    @ResponseBody
    public Set<TheoryLessons> getAllTheoryLessonsByEmployeeId(@RequestParam("id") Long id) {
        return theoryLessonsService.getAllTheoryLessonsByEmployeeId(id);
    }

    @GetMapping("/course")
    @ResponseBody
    public Optional<Course> findCourseByTheoryLessonsId(@RequestParam("id") Long id) {
        return theoryLessonsService.findCourseByTheoryLessonsId(id);
    }

    @GetMapping("/isPassed/byCourse")
    @ResponseBody
    public Boolean isTheoryLessonsPassedByCourse(@RequestBody Course course) {
        return theoryLessonsService.isTheoryLessonsPassedByCourse(course);
    }

    @GetMapping("/isPassed/byStudentId")
    @ResponseBody
    public Boolean isTheoryLessonsActiveByStudentId(@RequestParam("id") Long id) {
        return theoryLessonsService.isTheoryLessonsActiveByStudentId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<TheoryLessons> addTheoryLessons(@RequestParam("id") Long studentId,
                                                          @RequestBody Long lectureSeriesId) {
        return theoryLessonsService.addTheoryLessons(studentId, lectureSeriesId);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<TheoryLessons> changeLessonStatusByTheoryLessonsId(@RequestParam("id") Long id,
                                                                             @RequestBody LessonStatus status) {
        return theoryLessonsService.changeLessonStatusByTheoryLessonsId(id, status);
    }

}
