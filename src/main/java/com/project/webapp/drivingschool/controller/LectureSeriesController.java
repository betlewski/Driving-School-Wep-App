package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.model.LectureSeries;
import com.project.webapp.drivingschool.service.LectureSeriesService;
import com.project.webapp.drivingschool.utils.LectureSeriesStatus;
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
@RequestMapping("/api/series")
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

    @GetMapping("/all/employeeId")
    @ResponseBody
    public List<LectureSeries> getAllLectureSeriesByEmployeeId(@RequestParam("id") Long id) {
        return lectureSeriesService.getAllLectureSeriesByEmployeeId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<LectureSeries> addLectureSeries(@RequestParam("id") Long employeeId,
                                                          @RequestBody Set<Long> lecturesIds) {
        return lectureSeriesService.addLectureSeries(employeeId, lecturesIds);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<LectureSeries> changeStatusByLectureSeriesId(@RequestParam("id") Long id,
                                                                       @RequestBody LectureSeriesStatus status) {
        return lectureSeriesService.changeStatusByLectureSeriesId(id, status);
    }

}
