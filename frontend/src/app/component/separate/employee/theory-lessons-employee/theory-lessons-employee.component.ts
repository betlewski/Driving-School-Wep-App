import {Component, OnInit} from '@angular/core';
import {Utils} from "../../../../utils/utils";
import {AuthService} from "../../../../service/auth/auth.service";
import {LessonStatus} from "../../../../utils/lesson-status";
import {TheoryLessonsService} from "../../../../service/rest/theory-lessons/theory-lessons.service";
import {TheoryLessonsRest} from "../../../../utils/theory-lessons-rest";
import {TheoryLessons} from "../../../../model/theory-lessons.model";

@Component({
  selector: 'app-theory-lessons-employee',
  templateUrl: './theory-lessons-employee.component.html',
  styleUrls: ['./theory-lessons-employee.component.css']
})
/**
 * Panel pracownika do zarządzania zajęcia teoretycznymi.
 */
export class TheoryLessonsEmployeeComponent implements OnInit {

  requestedLessons: TheoryLessonsRest[] = [];
  activeLessons: TheoryLessonsRest[] = [];
  utils = new Utils();

  constructor(private authService: AuthService,
              private theoryLessonsService: TheoryLessonsService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllTheoryLessons(email);
  }

  private getAllTheoryLessons(email: string) {
    this.theoryLessonsService.findAllByEmployee(email)
      .subscribe(lessons => this.sortLessonsByStatus(lessons));
  }

  private sortLessonsByStatus(lessons: TheoryLessonsRest[]): void {
    lessons.forEach(value => {
      const lessonStatus = value.theoryLessons.lessonStatus;
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
    this.sortLessonsBySeriesId(this.requestedLessons);
    this.sortLessonsBySeriesId(this.activeLessons);
  }

  private sortLessonsBySeriesId(lessons: TheoryLessonsRest[]): void {
    lessons.sort((a, b) =>
      a.theoryLessons.lectureSeries.id - b.theoryLessons.lectureSeries.id);
  }

  public acceptRequest(lessons: TheoryLessons): void {
    if (lessons.id != null) {
      this.theoryLessonsService.acceptLessonRequest(lessons.id)
        .subscribe(() => this.refreshData());
    }
  }

  public rejectRequest(lessons: TheoryLessons): void {
    if (lessons.id != null) {
      this.theoryLessonsService.rejectLessonRequest(lessons.id)
        .subscribe(() => this.refreshData());
    }
  }

  public passLesson(lessons: TheoryLessons): void {
    if (lessons.id != null) {
      this.theoryLessonsService.passLesson(lessons.id)
        .subscribe(() => this.refreshData());
    }
  }

  public failLesson(lessons: TheoryLessons): void {
    if (lessons.id != null) {
      this.theoryLessonsService.failLesson(lessons.id)
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
