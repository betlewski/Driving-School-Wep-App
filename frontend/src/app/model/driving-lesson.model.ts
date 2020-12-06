import {Employee} from "./employee.model";
import {LessonStatus} from "../utils/lesson-status";

/**
 * Zajęcia praktyczne odbywane w ramach kursu.
 * (jazdy szkoleniowe)
 */
export class DrivingLesson {

  id: number;
  employee: Employee;
  lessonStatus: LessonStatus;
  startTime: Date;
  endTime: Date;

  constructor(id: number, employee: Employee, lessonStatus: LessonStatus,
              startTime: Date, endTime: Date) {
    this.id = id;
    this.employee = employee;
    this.lessonStatus = lessonStatus;
    this.startTime = startTime;
    this.endTime = endTime;
  }

}
