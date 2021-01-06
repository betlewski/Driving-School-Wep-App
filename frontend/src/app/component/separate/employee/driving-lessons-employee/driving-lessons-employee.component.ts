import {Component, OnInit} from '@angular/core';
import {DrivingLesson} from "../../../../model/driving-lesson.model";
import {Utils} from "../../../../utils/utils";
import {AuthService} from "../../../../service/auth/auth.service";
import {DrivingLessonService} from "../../../../service/rest/driving-lesson/driving-lesson.service";
import {LessonStatus} from "../../../../utils/lesson-status";
import {DrivingLessonRest} from "../../../../utils/driving-lesson-rest";

@Component({
  selector: 'app-driving-lessons-employee',
  templateUrl: './driving-lessons-employee.component.html',
  styleUrls: ['./driving-lessons-employee.component.css']
})
/**
 * Panel pracownika do zarządzania jazdami szkoleniowymi.
 */
export class DrivingLessonsEmployeeComponent implements OnInit {

  requestedLessons: DrivingLessonRest[] = [];
  activeLessons: DrivingLessonRest[] = [];
  utils = new Utils();

  constructor(private authService: AuthService,
              private drivingLessonService: DrivingLessonService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllDrivingLessons(email);
  }

  private getAllDrivingLessons(email: string) {
    this.drivingLessonService.findAllByEmployee(email)
      .subscribe(lessons => this.sortLessonsByStatus(lessons));
  }

  private sortLessonsByStatus(lessons: DrivingLessonRest[]): void {
    lessons.forEach(value => {
      const lessonStatus = value.drivingLesson.lessonStatus;
      if (lessonStatus != null) {
        switch (LessonStatus[lessonStatus] as unknown) {
          case LessonStatus.REQUESTED:
            this.requestedLessons.push(value);
            break;
          case LessonStatus.ACCEPTED:
            this.activeLessons.push(value);
            break;
          default:
            break;
        }
      }
    });
    this.sortLessonsByStartTime(this.requestedLessons);
    this.sortLessonsByStartTime(this.activeLessons);
  }

  private sortLessonsByStartTime(lessons: DrivingLessonRest[]): void {
    lessons.sort((a, b) =>
      // @ts-ignore
      new Date(a.drivingLesson.startTime).getTime() - new Date(b.drivingLesson.startTime).getTime());
  }

  public acceptRequest(lesson: DrivingLesson): void {
    if (lesson.id != null) {
      this.drivingLessonService.acceptLessonRequest(lesson.id)
        .subscribe(() => this.refreshData());
    }
  }

  public rejectRequest(lesson: DrivingLesson): void {
    if (lesson.id != null) {
      this.drivingLessonService.rejectLessonRequest(lesson.id)
        .subscribe(() => this.refreshData());
    }
  }

  public passLesson(lesson: DrivingLesson): void {
    if (lesson.id != null) {
      this.drivingLessonService.passLesson(lesson.id)
        .subscribe(() => this.refreshData());
    }
  }

  public failLesson(lesson: DrivingLesson): void {
    if (lesson.id != null) {
      this.drivingLessonService.failLesson(lesson.id)
        .subscribe(() => this.refreshData());
    }
  }

  private refreshData(): void {
    this.cleanData();
    this.ngOnInit();
  }

  private cleanData(): void {
    this.requestedLessons = [];
    this.activeLessons = [];
  }

}
