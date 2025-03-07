package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Lecture;
import com.project.webapp.drivingschool.data.model.TheoryLessons;
import com.project.webapp.drivingschool.data.service.TheoryLessonsService;
import com.project.webapp.drivingschool.data.utils.LessonStatus;
import com.project.webapp.drivingschool.data.utils.TheoryLessonsRest;
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

    @GetMapping("/all/byStudent")
    @ResponseBody
    public Set<TheoryLessons> getAllTheoryLessonsByStudentEmail(@RequestParam("email") String email) {
        return theoryLessonsService.getAllTheoryLessonsByStudentEmail(email);
    }

    @GetMapping("/all/byStudent/byStatus")
    @ResponseBody
    public Set<TheoryLessons> getTheoryLessonsByStudentEmailAndLessonStatus(@RequestParam("email") String email,
                                                                            @RequestParam("status") LessonStatus status) {
        return theoryLessonsService.getTheoryLessonsByStudentEmailAndLessonStatus(email, status);
    }

    @GetMapping("/all/byEmployee")
    @ResponseBody
    public Set<TheoryLessonsRest> getAllTheoryLessonsByEmployeeEmail(@RequestParam("email") String email) {
        return theoryLessonsService.getAllTheoryLessonsByEmployeeEmail(email);
    }

    @GetMapping("/active/byStudent")
    @ResponseBody
    public Optional<TheoryLessons> getActiveTheoryLessonsByStudentEmail(@RequestParam("email") String email) {
        return theoryLessonsService.getActiveTheoryLessonsByStudentEmail(email);
    }

    @GetMapping("/lectures/actual/byStudent")
    @ResponseBody
    public Set<Lecture> getAllLecturesForActualTheoryLessonsByStudentEmail(@RequestParam("email") String email) {
        return theoryLessonsService.getAllLecturesForActualTheoryLessonsByStudentEmail(email);
    }

    @GetMapping("/hours/passed/byCourse")
    @ResponseBody
    public Integer getCurrentlyPassedHoursOfTheoryLessonsByCourse(@RequestBody Course course) {
        return theoryLessonsService.getCurrentlyPassedHoursOfTheoryLessonsByCourse(course);
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

    @GetMapping("/isActive/byStudent")
    @ResponseBody
    public Boolean isTheoryLessonsActiveByStudentEmail(@RequestParam("email") String email) {
        return theoryLessonsService.isTheoryLessonsActiveByStudentEmail(email);
    }

    @PostMapping("/add")
    public ResponseEntity<TheoryLessons> addTheoryLessons(@RequestParam("email") String email,
                                                          @RequestBody Long lectureSeriesId) {
        return theoryLessonsService.addTheoryLessons(email, lectureSeriesId);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<TheoryLessons> changeLessonStatusByTheoryLessonsId(@RequestParam("id") Long id,
                                                                             @RequestBody LessonStatus status) {
        return theoryLessonsService.changeLessonStatusByTheoryLessonsId(id, status);
    }

}
