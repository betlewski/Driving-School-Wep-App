import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {DrivingLessonService} from "../../../../service/rest/driving-lesson/driving-lesson.service";
import {DrivingLesson} from "../../../../model/driving-lesson.model";
import {LessonStatus} from "../../../../utils/lesson-status";
import {Employee} from "../../../../model/employee.model";

@Component({
  selector: 'app-driving-lessons',
  templateUrl: './driving-lessons.component.html',
  styleUrls: ['./driving-lessons.component.css']
})
/**
 * Panel zarządzania jazdami szkoleniowymi.
 */
export class DrivingLessonsComponent implements OnInit {

  waitingLessons: DrivingLesson[] = [];
  passedLessons: DrivingLesson[] = [];
  failedLessons: DrivingLesson[] = [];

  constructor(private authService: AuthService,
              private drivingLessonService: DrivingLessonService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllDrivingLessons(email);
  }

  private getAllDrivingLessons(email: string) {
    this.drivingLessonService.findAllByEmail(email)
      .subscribe(lessons => this.sortLessonsByStatus(lessons));
  }

  private sortLessonsByStatus(lessons: DrivingLesson[]): void {
    lessons.forEach(lesson => {
      switch (LessonStatus[lesson.lessonStatus] as unknown) {
        case LessonStatus.REQUESTED:
        case LessonStatus.ACCEPTED:
          this.waitingLessons.push(lesson);
          break;
        case LessonStatus.PASSED:
          this.passedLessons.push(lesson);
          break;
        case LessonStatus.REJECTED:
        case LessonStatus.FAILED:
          this.failedLessons.push(lesson);
          break;
        default:
          break;
      }
    });
    this.sortLessonsByStartTime(this.waitingLessons);
    this.sortLessonsByStartTime(this.passedLessons);
    this.sortLessonsByStartTime(this.failedLessons);
  }

  private sortLessonsByStartTime(lessons: DrivingLesson[]): void {
    lessons.sort((a, b) =>
      a.startTime.getTime() - b.startTime.getTime());
  }

  public convertEmployee(employee: Employee): string {
    return employee.fullName.concat(" (")
      .concat(employee.phoneNumber).concat(")");
  }

  public translateStatus(status: LessonStatus): string {
    return LessonStatus.translate(status);
  }

  public convertDate(date: Date): string {
    const dateToParse = new Date(date);
    return dateToParse.toLocaleDateString().concat("  ")
      .concat(dateToParse.toLocaleTimeString());
  }

}
