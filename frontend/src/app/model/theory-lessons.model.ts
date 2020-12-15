import {LectureSeries} from "./lecture-series.model";
import {LessonStatus} from "../utils/lesson-status";

/**
 * Zajęcia teoretyczne przypisane do kursu.
 */
export class TheoryLessons {

  id: number;
  lectureSeries: LectureSeries;
  lessonStatus: LessonStatus;

  constructor(id: number, lectureSeries: LectureSeries, lessonStatus: LessonStatus) {
    this.id = id;
    this.lectureSeries = lectureSeries;
    this.lessonStatus = lessonStatus;
  }

}
