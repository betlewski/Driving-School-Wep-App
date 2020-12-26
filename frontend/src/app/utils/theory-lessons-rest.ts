import {Student} from "../model/student.model";
import {TheoryLessons} from "../model/theory-lessons.model";

/**
 * Model danych odbierany z REST API.
 * Zawiera zajęcia teoretyczne i przypisanego do nich kursanta.
 */
export class TheoryLessonsRest {

  student: Student;
  theoryLessons: TheoryLessons;

  constructor(student: Student, theoryLessons: TheoryLessons) {
    this.student = student;
    this.theoryLessons = theoryLessons;
  }

}
