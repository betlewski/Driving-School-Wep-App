import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../../../../service/auth/auth.service";
import {TheoryLessonsService} from "../../../../../service/rest/theory-lessons/theory-lessons.service";
import {TheoryLessons} from "../../../../../model/theory-lessons.model";
import {Utils} from "../../../../../utils/utils";
import {Lecture} from "../../../../../model/lecture.model";

@Component({
  selector: 'app-theory-lessons',
  templateUrl: './theory-lessons.component.html',
  styleUrls: ['./theory-lessons.component.css']
})
/**
 * Komponent do zarządzania cyklami wykładów kursanta.
 */
export class TheoryLessonsComponent implements OnInit {

  activeTheory: TheoryLessons | null = null;
  futureLectures: Lecture[] = [];
  pastLectures: Lecture[] = [];
  utils = new Utils();

  constructor(private router: Router,
              private authService: AuthService,
              private theoryLessonsService: TheoryLessonsService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.findActiveTheoryAndLectures(email);
  }

  private findActiveTheoryAndLectures(email: string) {
    this.theoryLessonsService.findActiveByStudent(email)
      .subscribe(theory => {
        this.activeTheory = theory;
        this.getLecturesForActive();
      });
  }

  public getLecturesForActive() {
    if (this.activeTheory != null) {
      this.splitLecturesByStartTime();
    }
  }

  private splitLecturesByStartTime(): void {
    const now = new Date();
    this.activeTheory?.lectureSeries.lectures.forEach(lecture => {
      if (new Date(lecture.endTime).getTime() <= now.getTime()) {
        this.pastLectures.push(lecture);
      } else {
        this.futureLectures.push(lecture);
      }
    });
    this.pastLectures = this.sortLecturesByStartTime(this.pastLectures);
    this.futureLectures = this.sortLecturesByStartTime(this.futureLectures);
  }

  private sortLecturesByStartTime(lectures: Lecture[]): Lecture[] {
    return lectures.sort((a, b) =>
      new Date(a.startTime).getTime() - new Date(b.startTime).getTime());
  }

}
