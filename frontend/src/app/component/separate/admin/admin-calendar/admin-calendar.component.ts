import {Component, OnInit} from '@angular/core';
import {CalendarComponent, colors} from "../../../common/calendar/calendar.component";
import {parseISO} from "date-fns";
import {Lecture} from "../../../../model/lecture.model";
import {DrivingLesson} from "../../../../model/driving-lesson.model";
import {LessonStatus} from "../../../../utils/lesson-status";
import {InternalExam} from "../../../../model/internal-exam.model";
import {ExamType} from "../../../../utils/exam-type";
import {Employee} from "../../../../model/employee.model";
import {Student} from "../../../../model/student.model";

@Component({
  selector: 'app-admin-calendar',
  templateUrl: './admin-calendar.component.html',
  styleUrls: ['./admin-calendar.component.css']
})
/**
 * Kalendarz z zajęciami przypisanymi do kursantów i pracowników.
 */
export class AdminCalendarComponent extends CalendarComponent implements OnInit {

  private lectureColor = colors.yellow;
  private drivingLessonColor = colors.green;
  private internalExamColor = colors.red;

  employees: Employee[] = [];
  chosenEmployee: Employee | null = null;
  students: Student[] = [];
  chosenStudent: Student | null = null;

  ngOnInit(): void {
    this.findAllEmployees();
    this.findAllStudents();
  }

  private findAllEmployees() {
    this.employeeService.findAllNotAdmins().subscribe(
      employees => {
        this.employees = employees;
        this.employees = this.employees.sort(
          // @ts-ignore
          (a, b) => a.fullName?.localeCompare(b.fullName));
      });
  }

  private findAllStudents() {
    this.studentService.findAll().subscribe(
      students => {
        this.students = students;
        this.students = this.students.sort(
          // @ts-ignore
          (a, b) => a.fullName?.localeCompare(b.fullName));
      });
  }

  public changeEmployee() {
    this.events = [];
    this.chosenStudent = null;
    // @ts-ignore
    if (this.chosenEmployee != null && this.chosenEmployee != ""
      && this.chosenEmployee.email != null) {
      this.getAllEventsForEmployee(this.chosenEmployee.email);
    }
  }

  public changeStudent() {
    this.events = [];
    this.chosenEmployee = null;
    // @ts-ignore
    if (this.chosenStudent != null && this.chosenStudent != ""
      && this.chosenStudent.email != null) {
      this.getAllEventsForStudent(this.chosenStudent.email);
    }
  }

  private getAllEventsForEmployee(email: string) {
    this.getAllLecturesForEmployee(email);
    this.getAllDrivingLessonsForEmployee(email);
    this.getAllInternalExamsForEmployee(email);
  }

  private getAllEventsForStudent(email: string) {
    this.getAllLecturesForStudent(email);
    this.getAllDrivingLessonsForStudent(email);
    this.getAllInternalExamsForStudent(email);
  }

  private getAllLecturesForEmployee(email: string) {
    this.lectureSeriesService.findAllLecturesByEmployee(email)
      .subscribe(lectures => {
        lectures.forEach(lecture => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromLecture(lecture),
              start: parseISO(lecture.startTime.toString()),
              end: parseISO(lecture.endTime.toString()),
              color: this.lectureColor
            }
          ];
        });
      });
  }

  private getAllLecturesForStudent(email: string) {
    this.theoryLessonsService.findAllLecturesByEmail(email)
      .subscribe(lectures => {
        lectures.forEach(lecture => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromLecture(lecture),
              start: parseISO(lecture.startTime.toString()),
              end: parseISO(lecture.endTime.toString()),
              color: this.lectureColor
            }
          ];
        });
      });
  }

  private getAllDrivingLessonsForEmployee(email: string) {
    this.drivingLessonService.findAllActualByEmployee(email)
      .subscribe(lessons => {
        lessons.forEach(restLesson => {
          const student = restLesson.student;
          const lesson = restLesson.drivingLesson;
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromDrivingLessonForEmployee(lesson, student),
              start: parseISO(lesson.startTime.toString()),
              end: parseISO(lesson.endTime.toString()),
              color: this.drivingLessonColor
            }
          ];
        })
      });
  }

  private getAllDrivingLessonsForStudent(email: string) {
    this.drivingLessonService.findAllByEmail(email)
      .subscribe(lessons => {
        lessons.forEach(lesson => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromDrivingLessonForStudent(lesson),
              start: parseISO(lesson.startTime.toString()),
              end: parseISO(lesson.endTime.toString()),
              color: this.drivingLessonColor
            }
          ];
        });
      });
  }

  private getAllInternalExamsForEmployee(email: string) {
    this.internalExamService.findAllActualByEmployee(email)
      .subscribe(exams => {
        exams.forEach(restExam => {
          const student = restExam.student;
          const exam = restExam.internalExam;
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromInternalExamForEmployee(exam, student),
              start: parseISO(exam.startTime.toString()),
              end: parseISO(exam.endTime.toString()),
              color: this.internalExamColor
            }
          ];
        });
      });
  }

  private getAllInternalExamsForStudent(email: string) {
    this.internalExamService.findAllByEmail(email)
      .subscribe(exams => {
        exams.forEach(exam => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromInternalExamForStudent(exam),
              start: parseISO(exam.startTime.toString()),
              end: parseISO(exam.endTime.toString()),
              color: this.internalExamColor
            }
          ];
        });
      });
  }

  private getEventTitleFromLecture(lecture: Lecture): string {
    const additionalInfo = lecture.additionalInfo == null ? "" :
      ("\n- ").concat(lecture.additionalInfo);
    return "WYKŁAD"
      .concat("\n- Temat: ").concat(lecture.subject)
      .concat(additionalInfo);
  }

  private getEventTitleFromDrivingLessonForEmployee(lesson: DrivingLesson, student: Student): string {
    if (lesson.employee != null && lesson.lessonStatus != null) {
      return "JAZDA SZKOLENIOWA"
        .concat("\n- Kursant: ").concat(<string>student.fullName)
        .concat(" (").concat(<string>student.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(lesson.lessonStatus));
    }
    return "ERROR";
  }

  private getEventTitleFromDrivingLessonForStudent(lesson: DrivingLesson): string {
    if (lesson.employee != null && lesson.lessonStatus != null) {
      return "JAZDA SZKOLENIOWA"
        .concat("\n- Instruktor: ").concat(<string>lesson.employee.fullName)
        .concat(" (").concat(<string>lesson.employee.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(lesson.lessonStatus));
    }
    return "ERROR";
  }

  private getEventTitleFromInternalExamForEmployee(exam: InternalExam, student: Student): string {
    if (exam.examType != null && exam.employee != null && exam.lessonStatus != null) {
      return ExamType.fullTranslate(exam.examType)
        .concat("\n- Kursant: ").concat(<string>student.fullName)
        .concat(" (").concat(<string>student.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(exam.lessonStatus));
    }
    return "ERROR";
  }

  private getEventTitleFromInternalExamForStudent(exam: InternalExam): string {
    if (exam.examType != null && exam.employee != null && exam.lessonStatus != null) {
      return ExamType.fullTranslate(exam.examType)
        .concat("\n- Prowadzący: ").concat(<string>exam.employee.fullName)
        .concat(" (").concat(<string>exam.employee.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(exam.lessonStatus));
    }
    return "ERROR";
  }

}
