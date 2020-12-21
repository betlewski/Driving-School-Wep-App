import {DrivingLesson} from "../model/driving-lesson.model";
import {Student} from "../model/student.model";

/**
 * Model danych odbierany z REST API.
 * Zawiera jazdę szkoleniową i przypisanego do niej kursanta.
 */
export class DrivingLessonRest {

  student: Student;
  drivingLesson: DrivingLesson;

  constructor(student: Student, drivingLesson: DrivingLesson) {
    this.student = student;
    this.drivingLesson = drivingLesson;
  }

}
