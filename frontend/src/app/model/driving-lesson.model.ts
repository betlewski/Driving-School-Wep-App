import {Employee} from "./employee.model";
import {LessonStatus} from "../utils/lesson-status";

/**
 * Zajęcia praktyczne odbywane w ramach kursu.
 * (jazdy szkoleniowe)
 */
export class DrivingLesson {

  id: number | null;
  employee: Employee | null;
  lessonStatus: LessonStatus | null;
  startTime: Date | null;
  endTime: Date | null;

  constructor(id: number | null, employee: Employee | null, lessonStatus: LessonStatus | null,
              startTime: Date | null, endTime: Date | null) {
    this.id = id;
    this.employee = employee;
    this.lessonStatus = lessonStatus;
    this.startTime = startTime;
    this.endTime = endTime;
  }

}
