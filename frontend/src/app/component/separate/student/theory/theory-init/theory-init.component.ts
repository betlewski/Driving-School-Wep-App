import {Component, OnInit} from '@angular/core';
import {LectureSeries} from "../../../../../model/lecture-series.model";
import {Lecture} from "../../../../../model/lecture.model";
import {AuthService} from "../../../../../service/auth/auth.service";
import {LectureSeriesService} from "../../../../../service/rest/lecture-series/lecture-series.service";
import {Utils} from "../../../../../utils/utils";
import {TheoryLessonsService} from "../../../../../service/rest/theory-lessons/theory-lessons.service";

@Component({
  selector: 'app-theory-init',
  templateUrl: './theory-init.component.html',
  styleUrls: ['./theory-init.component.css']
})
/**
 * Komponent inicjalizacji zajęć teoretycznych kursanta.
 */
export class TheoryInitComponent implements OnInit {

  seriesToInit: LectureSeries[] = [];
  chosenSeries: LectureSeries | null = null;
  lecturesForChosen: Lecture[] = [];
  utils = new Utils();

  constructor(private authService: AuthService,
              private lectureSeriesService: LectureSeriesService,
              private theoryLessonsService: TheoryLessonsService) {
  }

  ngOnInit(): void {
    this.findAllFreeSeries();
  }

  private findAllFreeSeries() {
    this.lectureSeriesService.findAllFreeSeries()
      .subscribe(series => this.seriesToInit = series);
  }

  public showLecturesForChosen() {
    // @ts-ignore
    if (this.chosenSeries != null && this.chosenSeries != "") {
      this.lecturesForChosen = this.chosenSeries.lectures;
      this.sortLecturesForChosenByStartTime();
    } else {
      this.lecturesForChosen = [];
    }
  }

  private sortLecturesForChosenByStartTime(): void {
    this.lecturesForChosen.sort((a, b) =>
      new Date(a.startTime).getTime() - new Date(b.startTime).getTime());
  }

  public isLecturesForChosenEmpty(): boolean {
    return this.lecturesForChosen.length == 0;
  }

  public init() {
    // @ts-ignore
    if (this.chosenSeries != null && this.chosenSeries != "" && this.chosenSeries.id != null) {
      const email = this.authService.getUserEmail();
      this.theoryLessonsService.add(email, this.chosenSeries.id)
        .subscribe(() => window.location.replace("home/student/theory"));
    }
  }

}
