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

import java.util.Map;
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

    @GetMapping("/byEmail")
    @ResponseBody
    public Optional<Course> getActiveCourseByEmail(@RequestParam("email") String email) {
        return courseService.getActiveCourseByEmail(email);
    }

    @GetMapping("/exist/active/byEmail")
    @ResponseBody
    public Boolean checkExistingActiveCourseByEmail(@RequestParam("email") String email) {
        return courseService.checkExistingActiveCourseByEmail(email);
    }

    @GetMapping("/isAgeEnough/byEmail/byCategory")
    @ResponseBody
    public Boolean checkRequiredAgeByEmailAndLicenceCategory(@RequestParam("email") String email,
                                                             @RequestParam("category") LicenceCategory category) {
        return courseService.checkRequiredAgeByEmailAndLicenceCategory(email, category);
    }

    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestParam("email") String email,
                                            @RequestBody LicenceCategory category) {
        return courseService.addCourse(email, category);
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

    @GetMapping("/report/byEmail")
    @ResponseBody
    public Optional<CourseReport> getReportByEmail(@RequestParam("email") String email) {
        return courseReportService.getReportByEmail(email);
    }

    @GetMapping("/report/byCourse")
    @ResponseBody
    public CourseReport getReportByCourse(@RequestBody Course course) {
        return courseReportService.getReportByCourse(course);
    }

    @GetMapping("/report/all")
    @ResponseBody
    public Map<String, CourseReport> getAllReports() {
        return courseReportService.getAllReports();
    }

}
