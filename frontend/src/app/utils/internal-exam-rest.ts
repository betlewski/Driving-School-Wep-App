import {Student} from "../model/student.model";
import {InternalExam} from "../model/internal-exam.model";

/**
 * Model danych odbierany z REST API.
 * Zawiera egzamin wewnętrzny i przypisanego do niego kursanta.
 */
export class InternalExamRest {

  student: Student;
  internalExam: InternalExam;

  constructor(student: Student, internalExam: InternalExam) {
    this.student = student;
    this.internalExam = internalExam;
  }

}
