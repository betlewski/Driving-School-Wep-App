import {Component, OnInit} from '@angular/core';
import {Lecture} from "../../../../../model/lecture.model";
import {Utils} from "../../../../../utils/utils";
import {TextConstants} from "../../../../../utils/text-constants";
import {LectureService} from "../../../../../service/rest/lecture/lecture.service";
import {AuthService} from "../../../../../service/auth/auth.service";
import {LectureSeriesService} from "../../../../../service/rest/lecture-series/lecture-series.service";

@Component({
  selector: 'app-lecture-series-init',
  templateUrl: './lecture-series-init.component.html',
  styleUrls: ['./lecture-series-init.component.css']
})
/**
 * Komponent do tworzenia przez wykładowcę nowych cyklów wykładów.
 */
export class LectureSeriesInitComponent implements OnInit {

  subject: string | null = null;
  addInfo: string | null = null;
  startTime: Date | null = null;
  endTime: Date | null = null;
  lectures: Lecture[] = [];

  currentlyHours: number = 0;
  requiredHours: number = 30;
  correctHours: boolean = false;
  feedback: string = "";
  utils = new Utils();

  constructor(private authService: AuthService,
              private lectureSeriesService: LectureSeriesService,
              private lectureService: LectureService) {
  }

  ngOnInit(): void {
    this.getAllFreeLectures();
  }

  private getAllFreeLectures(): void {
    this.lectureService.findAllFreeLectures().subscribe(lectures => {
      this.lectures = lectures;
      this.sortLecturesByStartTime();
      this.calculateAndCheckLecturesHours();
    });
  }

  private sortLecturesByStartTime(): void {
    this.lectures.sort((a, b) =>
      new Date(a.startTime).getTime() - new Date(b.startTime).getTime());
  }

  private calculateAndCheckLecturesHours(): void {
    this.calculateCurrentlyHours();
    this.lectureService.checkIfSumHoursCorrect(this.lectures)
      .subscribe(result => {
        if (result) {
          this.feedback = TextConstants.LECTURE_SERIES_CORRECT_HOURS;
          this.correctHours = true;
        } else {
          this.feedback = TextConstants.LECTURE_SERIES_INIT;
          this.correctHours = false;
        }
      });
  }

  private calculateCurrentlyHours(): void {
    let currentlyHours = 0;
    for (const lecture of this.lectures) {
      const hours = Math.abs(new Date(lecture.endTime).getTime()
        - new Date(lecture.startTime).getTime()) / 36e5;
      currentlyHours += Math.round(hours * 100) / 100;
    }
    this.currentlyHours = currentlyHours;
  }

  public addLecture(): void {
    if (this.checkIfDataNotEmpty()) {
      // @ts-ignore
      const lecture = new Lecture(0, this.subject, this.startTime, this.endTime, this.addInfo);
      this.lectureService.addLecture(lecture).subscribe(lecture => {
        this.lectures.push(lecture);
        this.sortLecturesByStartTime();
        this.calculateAndCheckLecturesHours();
        this.cleanLectureData();
      }, () => this.feedback = TextConstants.LESSON_NEW_INVALID_TIME);
    } else {
      this.feedback = TextConstants.LECTURE_SERIES_INCOMPLETE_DATA;
    }
  }

  private checkIfDataNotEmpty(): boolean {
    return Utils.checkStringIfNotEmpty(this.subject)
      && this.startTime != null && this.endTime != null;
  }

  public removeLecture(lecture: Lecture): void {
    this.lectureService.deleteLecture(lecture.id).subscribe(() => {
      this.lectures = this.lectures.filter(
        value => value != lecture);
      this.calculateAndCheckLecturesHours();
    });
  }

  public addSeries(): void {
    const email = this.authService.getUserEmail();
    const lectureIds = this.lectures.map(lecture => lecture.id);
    this.lectureSeriesService.addSeries(email, lectureIds)
      .subscribe(() => {
        this.feedback = TextConstants.LECTURE_SERIES_SUCCESSFUL;
        this.cleanAllData();
      }, () => this.feedback = TextConstants.LECTURE_SERIES_ERROR);
  }

  private cleanAllData(): void {
    this.lectures = [];
    this.currentlyHours = 0;
    this.correctHours = false;
    this.cleanLectureData();
  }

  private cleanLectureData(): void {
    this.subject = null;
    this.addInfo = null;
    this.startTime = null;
    this.endTime = null;
  }

}
