import {Component, OnInit} from '@angular/core';
import {CourseService} from "../../../../service/rest/course/course.service";
import {CourseReport} from "../../../../model/course-report.model";
import {Utils} from "../../../../utils/utils";
import {CourseStatus} from "../../../../utils/course-status";

@Component({
  selector: 'app-course-admin',
  templateUrl: './course-admin.component.html',
  styleUrls: ['./course-admin.component.css']
})
/**
 * Panel wszystkich aktywnych kursów nauki.
 */
export class CourseAdminComponent implements OnInit {

  resultMap: Map<String, CourseReport> = new Map<String, CourseReport>();
  utils = new Utils();
  courseStatus = CourseStatus;

  constructor(private courseService: CourseService) {
  }

  ngOnInit(): void {
    this.getAllReports();
  }

  private getAllReports(): void {
    this.courseService.getAllReports().subscribe(
      map => {
        this.resultMap = map;
        this.resultMap = new Map([...this.resultMap].sort());
      });
  }

}
