package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.LectureSeries;
import com.project.webapp.drivingschool.data.service.LectureSeriesService;
import com.project.webapp.drivingschool.data.utils.LectureSeriesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Kontroler dla cyklu wykładów w szkole jazdy
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/series")
public class LectureSeriesController {

    private LectureSeriesService lectureSeriesService;

    @Autowired
    public LectureSeriesController(LectureSeriesService lectureSeriesService) {
        this.lectureSeriesService = lectureSeriesService;
    }

    @GetMapping("/all/free")
    @ResponseBody
    public List<LectureSeries> getAllLectureSeriesPossibleToJoin() {
        return lectureSeriesService.getAllLectureSeriesPossibleToJoin();
    }

    @GetMapping("/all/byEmployee")
    @ResponseBody
    public List<LectureSeries> getAllLectureSeriesByEmployeeEmail(@RequestParam("email") String email) {
        return lectureSeriesService.getAllLectureSeriesByEmployeeEmail(email);
    }

    @PostMapping("/add")
    public ResponseEntity<LectureSeries> addLectureSeries(@RequestParam("email") String employeeEmail,
                                                          @RequestBody Set<Long> lecturesIds) {
        return lectureSeriesService.addLectureSeries(employeeEmail, lecturesIds);
    }

    @PutMapping("/edit/status")
    public ResponseEntity<LectureSeries> changeStatusByLectureSeriesId(@RequestParam("id") Long id,
                                                                       @RequestBody LectureSeriesStatus status) {
        return lectureSeriesService.changeStatusByLectureSeriesId(id, status);
    }

}
