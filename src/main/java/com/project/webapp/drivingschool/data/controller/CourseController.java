package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.report.CourseReport;
import com.project.webapp.drivingschool.data.service.CourseReportService;
import com.project.webapp.drivingschool.data.service.CourseService;
import com.project.webapp.drivingschool.data.utils.CourseStatus;
import com.project.webapp.drivingschool.data.utils.LicenceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Kontroler dla kursów nauki jazdy i ich raportów
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/course")
public class CourseController {

    private CourseService courseService;
    private CourseReportService courseReportService;

    @Autowired
    public CourseController(CourseService courseService,
                            CourseReportService courseReportService) {
        this.courseService = courseService;
        this.courseReportService = courseReportService;
    }

    @GetMapping("/byStudentId")
    @ResponseBody
    public Optional<Course> getActiveCourseByStudentId(@RequestParam("id") Long id) {
        return courseService.getActiveCourseByStudentId(id);
    }

    @GetMapping("/exist/active/byStudentId")
    @ResponseBody
    public Boolean checkExistingActiveCourseByStudentId(@RequestParam("id") Long id) {
        return courseService.checkExistingActiveCourseByStudentId(id);
    }

    @GetMapping("/isAgeEnough/byStudentId/byCategory")
    @ResponseBody
    public Boolean checkRequiredAgeByStudentIdAndLicenceCategory(@RequestParam("id") Long id,
                                                                 @RequestParam("category") LicenceCategory category) {
        return courseService.checkRequiredAgeByStudentIdAndLicenceCategory(id, category);
    }

    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestParam("id") Long id,
                                            @RequestBody LicenceCategory category) {
        return courseService.addCourse(id, category);
    }

    @PutMapping("/finish")
    public ResponseEntity<Course> finishCourseByCourseId(@RequestBody Long id) {
        return courseService.finishCourseByCourseId(id);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<Course> changeStatusByCourseId(@RequestParam("id") Long id,
                                                         @RequestBody CourseStatus status) {
        return courseService.changeStatusByCourseId(id, status);
    }

    @GetMapping("/report/byStudentId")
    @ResponseBody
    public Optional<CourseReport> getReportByStudentId(@RequestParam("id") Long id) {
        return courseReportService.getReportByStudentId(id);
    }

    @GetMapping("/report/byCourse")
    @ResponseBody
    public CourseReport getReportByCourse(@RequestBody Course course) {
        return courseReportService.getReportByCourse(course);
    }

}
