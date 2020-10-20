package com.project.webapp.drivingschool.controller;

import com.project.webapp.drivingschool.model.Lecture;
import com.project.webapp.drivingschool.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Kontroler dla pojedynczych wykładów w ramach cyklu wykładów
 */
@CrossOrigin
@RestController
@RequestMapping("/api/lecture")
public class LectureController {

    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/all/employeeId")
    @ResponseBody
    public Set<Lecture> getAllLecturesByEmployeeId(@RequestParam("id") Long id) {
        return lectureService.getAllLecturesByEmployeeId(id);
    }

    @GetMapping("/all/seriesId")
    @ResponseBody
    public Set<Lecture> getAllLecturesByLectureSeriesId(@RequestParam("id") Long id) {
        return lectureService.getAllLecturesByLectureSeriesId(id);
    }

    @GetMapping("/all/free")
    @ResponseBody
    public Set<Lecture> getAllFreeLectures() {
        return lectureService.getAllFreeLectures();
    }

    @GetMapping("/checkExisting/lectureId")
    @ResponseBody
    public Boolean checkExistingInLectureSeriesByLectureId(@RequestParam("id") Long id) {
        return lectureService.checkExistingInLectureSeriesByLectureId(id);
    }

    @GetMapping("/newOrOngoing/employeeId")
    @ResponseBody
    public Set<Lecture> getAllNewOrOngoingLecturesByEmployeeId(@RequestParam("id") Long id) {
        return lectureService.getAllNewOrOngoingLecturesByEmployeeId(id);
    }

    @GetMapping("/allHours/seriesId")
    @ResponseBody
    public Integer getAllHoursOfLecturesByLectureSeriesId(@RequestParam("id") Long id) {
        return lectureService.getAllHoursOfLecturesByLectureSeriesId(id);
    }

    @GetMapping("/passedHours/seriesId")
    @ResponseBody
    public Integer getCurrentlyPassedHoursOfLecturesByLectureSeriesId(@RequestParam("id") Long id) {
        return lectureService.getCurrentlyPassedHoursOfLecturesByLectureSeriesId(id);
    }

    @GetMapping("/checkRequiredHours/lecturesSet")
    @ResponseBody
    public Boolean checkIfSumEqualsRequiredTheoryHoursForLecturesSet(@RequestBody Set<Lecture> lectures) {
        return lectureService.checkIfSumEqualsRequiredTheoryHoursForLecturesSet(lectures);
    }

    @GetMapping("/checkTimes")
    @ResponseBody
    public Boolean checkIfStartTimeIsBeforeEndTime(@RequestParam("start") LocalDateTime startTime,
                                                   @RequestParam("end") LocalDateTime endTime) {
        return lectureService.checkIfStartTimeIsBeforeEndTime(startTime, endTime);
    }

    @PostMapping("/add")
    public ResponseEntity<Lecture> addLecture(@RequestBody Lecture lecture) {
        return lectureService.addLecture(lecture);
    }

    @PutMapping("/edit")
    public ResponseEntity<Lecture> editLecture(@RequestParam("id") Long id,
                                               @RequestBody Lecture newLecture) {
        return lectureService.editLecture(id, newLecture);
    }

}
