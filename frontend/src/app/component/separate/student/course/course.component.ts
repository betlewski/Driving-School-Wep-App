import {Component, OnInit} from '@angular/core';
import {CourseService} from "../../../../service/rest/course/course.service";
import {AuthService} from "../../../../service/auth/auth.service";
import {CourseStatus} from "../../../../utils/course-status";
// @ts-ignore
import * as CanvasJS from "../../../../../assets/canvas-js/canvasjs.min";
import {CourseReport} from "../../../../model/course-report.model";

CanvasJS.addColorSet("colorSet", ["#5b616f", "#f1f2f3"]);

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
/**
 * Panel informacji o aktywnym kursie.
 */
export class CourseComponent implements OnInit {

  comment: string = "";
  passedCoursePercent: number = 0;
  extraDrivingLessonsHours: number = 0;
  startTime: Date | null = null;
  endTime: Date | null = null;

  private IMAGE_PATH = "../../../../../assets/course-status/";
  imageSrc: string = "";
  chart: CanvasJS;

  constructor(private authService: AuthService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.courseService.getReportByEmail(email)
      .subscribe(course => {
        if (course != null) {
          this.getDisplayedData(course);
          this.showStatusImage(course.courseStatus);
          this.drawTheoryChart(course.passedTheoryHours, course.theoryHours);
          this.drawPracticeChart(course.passedPracticeHours, course.practiceHours);
        } else {
          this.comment = "Nie znaleziono aktywnego kursu. Rozpocznij nowy...";
        }
      });
  }

  private getDisplayedData(course: CourseReport): void {
    this.comment = course.comment;
    this.passedCoursePercent = course.passedCoursePercent;
    this.extraDrivingLessonsHours = course.extraDrivingLessonsHours;
    this.startTime = course.startDate;
    this.endTime = course.endTime;
  }

  private showStatusImage(status: CourseStatus): void {
    const imageNumber = CourseStatus.getImageNumber(status);
    this.imageSrc = this.IMAGE_PATH + imageNumber + ".jpg";
  }

  private drawTheoryChart(passedTheoryHours: number, theoryHours: number): void {
    this.drawChart(passedTheoryHours, theoryHours, "theoryChart");
  }

  private drawPracticeChart(passedPracticeHours: number, practiceHours: number): void {
    this.drawChart(passedPracticeHours, practiceHours, "practiceChart");
  }

  private drawChart(passedHours: number, allHours: number, chartId: string) {
    this.chart = new CanvasJS.Chart(chartId, {
      animationEnabled: true,
      colorSet: "colorSet",
      data: [{
        type: "pie",
        toolTipContent: "<b>{name}</b>: {y}h",
        dataPoints: [
          {y: passedHours, name: "Ukończono"},
          {y: allHours - passedHours, name: "Pozostało"},
        ]
      }]
    });
    this.chart.render();
  }

}
