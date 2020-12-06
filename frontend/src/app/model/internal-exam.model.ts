import {ExamType} from "../utils/exam-type";
import {Employee} from "./employee.model";
import {LessonStatus} from "../utils/lesson-status";

/**
 * Egzamin wewnętrzny odbywany w ramach kursu.
 */
export class InternalExam {

  id: number;
  examType: ExamType;
  employee: Employee;
  startTime: Date;
  endTime: Date;
  lessonStatus: LessonStatus;
  result: number;
  isPassed: boolean;

  constructor(id: number, examType: ExamType, employee: Employee,
              startTime: Date, endTime: Date, lessonStatus: LessonStatus,
              result: number, isPassed: boolean) {
    this.id = id;
    this.examType = examType;
    this.employee = employee;
    this.startTime = startTime;
    this.endTime = endTime;
    this.lessonStatus = lessonStatus;
    this.result = result;
    this.isPassed = isPassed;
  }

}
