import {Component, OnInit} from '@angular/core';
import {CourseService} from "../../../../service/rest/course/course.service";
import {AuthService} from "../../../../service/auth/auth.service";
import {CourseReport} from "../../../../model/course-report.model";
import {CourseStatus} from "../../../../utils/course-status";
import {ProcessingStatus} from "../../../../utils/processing-status";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
/**
 * Panel informacji o aktywnym kursie.
 */
export class CourseComponent implements OnInit {

  courseReport: CourseReport | null = null;
  courseStatus: string = "";
  paymentStatus: string = "";

  constructor(private authService: AuthService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.courseService.getReportByEmail(email)
      .subscribe(data => {
        this.courseReport = data;
        this.courseStatus = CourseStatus.translate(data.courseStatus);
        this.paymentStatus = ProcessingStatus.translate(data.paymentStatus);
      });
  }

}
