import {Component, OnInit} from '@angular/core';
import {Lecture} from "../../../../../model/lecture.model";
import {Utils} from "../../../../../utils/utils";
import {Router} from "@angular/router";
import {AuthService} from "../../../../../service/auth/auth.service";
import {LectureSeries} from "../../../../../model/lecture-series.model";
import {LectureSeriesService} from "../../../../../service/rest/lecture-series/lecture-series.service";
import {LectureSeriesStatus} from "../../../../../utils/lecture-series-status";

@Component({
  selector: 'app-lecture-series',
  templateUrl: './lecture-series.component.html',
  styleUrls: ['./lecture-series.component.css']
})
/**
 * Komponent do zarządzania przez wykładowcę cyklami wykładów.
 */
export class LectureSeriesComponent implements OnInit {

  allSeries: LectureSeries[] = [];
  chosenSeries: LectureSeries | null = null;
  seriesStatusValues: string[] = LectureSeriesStatus.stringValues();
  seriesStatus: LectureSeriesStatus | null = null;

  futureLectures: Lecture[] = [];
  pastLectures: Lecture[] = [];

  viewMode: boolean = true;
  utils = new Utils();

  constructor(private router: Router,
              private authService: AuthService,
              private lectureSeriesService: LectureSeriesService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.findAllSeries(email);
  }

  private findAllSeries(email: string) {
    this.lectureSeriesService.findAllSeriesByEmployee(email)
      .subscribe(series => this.allSeries = series);
  }

  public changeSeries() {
    this.cleanData();
    // @ts-ignore
    if (this.chosenSeries != null && this.chosenSeries != "") {
      this.seriesStatus = this.chosenSeries.status;
      this.splitLecturesByStartTime();
    } else {
      this.chosenSeries = null;
    }
  }

  private splitLecturesByStartTime(): void {
    const now = new Date();
    this.chosenSeries?.lectures.forEach(lecture => {
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

  public editStatus() {
    // @ts-ignore
    if (this.chosenSeries != null && this.chosenSeries != "") {
      this.viewMode = false;
    }
  }

  public saveStatus() {
    const seriesId = this.chosenSeries?.id;
    const seriesStatus = this.seriesStatus;
    // @ts-ignore
    if (seriesId != null && seriesStatus != null && seriesStatus != "") {
      this.lectureSeriesService.editSeriesStatus(seriesId, seriesStatus)
        .subscribe(() => this.refreshData());
    }
  }

  private refreshData(): void {
    this.cleanData();
    this.ngOnInit();
    this.chosenSeries = null;
    this.viewMode = true;
  }

  private cleanData(): void {
    this.seriesStatus = null;
    this.futureLectures = [];
    this.pastLectures = [];
  }

  public addSeries() {
    this.router.navigate(["/home/employee/lecture/init"]).then();
  }

}
