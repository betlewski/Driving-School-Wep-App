import {ExamType} from "../utils/exam-type";
import {Employee} from "./employee.model";
import {LessonStatus} from "../utils/lesson-status";

/**
 * Egzamin wewnętrzny odbywany w ramach kursu.
 */
export class InternalExam {

  id: number | null;
  examType: ExamType | null;
  employee: Employee | null;
  startTime: Date | null;
  endTime: Date | null;
  lessonStatus: LessonStatus | null;
  result: number | null;
  isPassed: boolean | null;

  constructor(id: number | null, examType: ExamType | null, employee: Employee | null,
              startTime: Date | null, endTime: Date | null, lessonStatus: LessonStatus | null,
              result: number | null, isPassed: boolean | null) {
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
