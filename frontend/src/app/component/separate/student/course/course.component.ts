import {Component, OnInit} from '@angular/core';
import {CourseService} from "../../../../service/rest/course/course.service";
import {AuthService} from "../../../../service/auth/auth.service";
import {CourseReport} from "../../../../model/course-report.model";
import {CourseStatus} from "../../../../utils/course-status";

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
  passedCoursePercent: number = 0;

  imagePath = "../../../../../assets/course-status/";
  imageSrc: string = "";

  constructor(private authService: AuthService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.courseService.getReportByEmail(email)
      .subscribe(course => {
        if (course != null) {
          this.courseReport = course;
          this.passedCoursePercent = course.passedCoursePercent;
          this.showStatusImage(course.courseStatus);
        }
      });
  }

  private showStatusImage(status: CourseStatus): void {
    const imageNumber = CourseStatus.getImageNumber(status);
    this.imageSrc = this.imagePath + imageNumber + ".jpg";
  }

}
