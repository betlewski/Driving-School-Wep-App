import {Employee} from "./employee.model";
import {LectureSeriesStatus} from "../utils/lecture-series-status";
import {Lecture} from "./lecture.model";

/**
 * Cykl wykładów prowadzony w ramach części teoretycznej.
 */
export class LectureSeries {

  id: number;
  employee: Employee;
  status: LectureSeriesStatus;
  lectures: Lecture[];

  constructor(id: number, employee: Employee, status: LectureSeriesStatus, lectures: Lecture[]) {
    this.id = id;
    this.employee = employee;
    this.status = status;
    this.lectures = lectures;
  }

}
