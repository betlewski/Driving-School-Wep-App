import {Component, OnInit} from '@angular/core';
import {Employee} from "../../../../model/employee.model";
import {AuthService} from "../../../../service/auth/auth.service";
import {EmployeeService} from "../../../../service/rest/employee/employee.service";
import {EmployeeRole} from "../../../../utils/employee-role";
import {LessonStatus} from "../../../../utils/lesson-status";
import {ExamType} from "../../../../utils/exam-type";
import {InternalExamService} from "../../../../service/rest/internal-exam/internal-exam.service";
import {InternalExam} from "../../../../model/internal-exam.model";
import {TextConstants} from "../../../../utils/text-constants";
import {CourseService} from "../../../../service/rest/course/course.service";
import {Utils} from "../../../../utils/utils";

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
/**
 * Panel zarządzania egzaminami wewnętrznymi.
 */
export class ExamComponent implements OnInit {

  waitingExams: InternalExam[] = [];
  passedExams: InternalExam[] = [];
  failedExams: InternalExam[] = [];

  possibleExamType: ExamType | null = null;
  teachers: Employee[] = [];
  chosenTeacher: Employee | null = null;
  startTime: Date | null = null;
  endTime: Date | null = null;

  feedback: string = "";
  utils = new Utils();

  constructor(private authService: AuthService,
              private internalExamService: InternalExamService,
              private employeeService: EmployeeService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getExamTypeAndTeachers(email);
    this.getAllExams(email);
  }

  private getExamTypeAndTeachers(email: string) {
    this.courseService.findActiveCourseByEmail(email)
      .subscribe(course => {
        if (course != null && course.licenseCategory != null) {
          this.possibleExamType = ExamType.getExamTypeByCourseStatus(course.courseStatus);
          const role = EmployeeRole.getEmployeeRoleForExam(course.licenseCategory, this.possibleExamType);
          this.employeeService.findAllByRole(role).subscribe(
            employees => this.teachers = employees);
        }
      });
  }

  private getAllExams(email: string) {
    this.internalExamService.findAllByStudent(email)
      .subscribe(exams => this.sortExamsByStatusAndPassed(exams));
  }

  private sortExamsByStatusAndPassed(exams: InternalExam[]): void {
    exams.forEach(exam => {
      const lessonStatus = exam.lessonStatus;
      if (lessonStatus != null) {
        switch (LessonStatus[lessonStatus] as unknown) {
          case LessonStatus.REQUESTED:
          case LessonStatus.ACCEPTED:
            this.waitingExams.push(exam);
            break;
          case LessonStatus.PASSED:
            if (exam.isPassed) {
              this.passedExams.push(exam);
            } else {
              this.failedExams.push(exam);
            }
            break;
          case LessonStatus.REJECTED:
          case LessonStatus.FAILED:
            this.failedExams.push(exam);
            break;
          default:
            break;
        }
      }
    });
    this.sortExamsByStartTime(this.waitingExams);
    this.sortExamsByStartTime(this.passedExams);
    this.sortExamsByStartTime(this.failedExams);
  }

  private sortExamsByStartTime(exams: InternalExam[]): void {
    exams.sort((a, b) =>
      // @ts-ignore
      new Date(a.startTime).getTime() - new Date(b.startTime).getTime());
  }

  public setEndTime(startTime: Date): void {
    let newTime = new Date(startTime);
    newTime.setHours(newTime.getHours() + 3);
    // @ts-ignore
    this.endTime = newTime.toISOString().substr(0, 19);
  }

  public request(): void {
    // @ts-ignore
    if (this.chosenTeacher != "" && this.chosenTeacher != null && this.chosenTeacher.email != null
      && this.possibleExamType != null && this.startTime != null && this.endTime != null) {
      const studentEmail = this.authService.getUserEmail();
      const employeeEmail = this.chosenTeacher.email;
      const exam = new InternalExam(null, this.possibleExamType, null,
        this.startTime, this.endTime, null, null, null);
      this.internalExamService.addExam(studentEmail, employeeEmail, exam)
        .subscribe(() => {
            this.refreshData();
            this.feedback = TextConstants.LESSON_NEW_SUCCESSFUL;
          },
          error => {
            if (error.status == 400) {
              this.feedback = TextConstants.LESSON_NEW_INVALID_TIME;
            }
          });
    } else {
      this.feedback = TextConstants.LESSON_NEW_INCOMPLETE_DATA;
    }
  }

  private refreshData(): void {
    this.cleanData();
    this.ngOnInit();
  }

  private cleanData(): void {
    this.waitingExams = [];
    this.passedExams = [];
    this.failedExams = [];
    this.teachers = [];
    this.chosenTeacher = null;
    this.startTime = null;
    this.endTime = null;
    this.feedback = "";
  }

}
