package com.project.webapp.drivingschool.data.controller;

import com.project.webapp.drivingschool.data.model.Lecture;
import com.project.webapp.drivingschool.data.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Kontroler dla pojedynczych wykładów w ramach cyklu wykładów
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/lecture")
public class LectureController {

    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/all/byEmployee")
    @ResponseBody
    public Set<Lecture> getAllLecturesByEmployeeEmail(@RequestParam("email") String email) {
        return lectureService.getAllLecturesByEmployeeEmail(email);
    }

    @GetMapping("/all/bySeriesId")
    @ResponseBody
    public Set<Lecture> getAllLecturesByLectureSeriesId(@RequestParam("id") Long id) {
        return lectureService.getAllLecturesByLectureSeriesId(id);
    }

    @GetMapping("/all/free")
    @ResponseBody
    public Set<Lecture> getAllFreeLectures() {
        return lectureService.getAllFreeLectures();
    }

    @GetMapping("/exist/byLectureId")
    @ResponseBody
    public Boolean checkExistingInLectureSeriesByLectureId(@RequestParam("id") Long id) {
        return lectureService.checkExistingInLectureSeriesByLectureId(id);
    }

    @GetMapping("all/newOrOngoing/byEmployee")
    @ResponseBody
    public Set<Lecture> getAllNewOrOngoingLecturesByEmployeeEmail(@RequestParam("email") String email) {
        return lectureService.getAllNewOrOngoingLecturesByEmployeeEmail(email);
    }

    @GetMapping("/hours/all/bySeriesId")
    @ResponseBody
    public Integer getAllHoursOfLecturesByLectureSeriesId(@RequestParam("id") Long id) {
        return lectureService.getAllHoursOfLecturesByLectureSeriesId(id);
    }

    @GetMapping("/hours/passed/bySeriesId")
    @ResponseBody
    public Integer getCurrentlyPassedHoursOfLecturesByLectureSeriesId(@RequestParam("id") Long id) {
        return lectureService.getCurrentlyPassedHoursOfLecturesByLectureSeriesId(id);
    }

    @GetMapping("/hours/isEnough/byLecturesSet")
    @ResponseBody
    public Boolean checkIfSumEqualsRequiredTheoryHoursForLecturesSet(@RequestBody Set<Lecture> lectures) {
        return lectureService.checkIfSumEqualsRequiredTheoryHoursForLecturesSet(lectures);
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
