import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {DrivingLessonService} from "../../../../service/rest/driving-lesson/driving-lesson.service";
import {DrivingLesson} from "../../../../model/driving-lesson.model";
import {LessonStatus} from "../../../../utils/lesson-status";
import {Employee} from "../../../../model/employee.model";
import {EmployeeService} from "../../../../service/rest/employee/employee.service";
import {CourseService} from "../../../../service/rest/course/course.service";
import {EmployeeRole} from "../../../../utils/employee-role";
import {TextConstants} from "../../../../utils/text-constants";

@Component({
  selector: 'app-driving-lessons',
  templateUrl: './driving-lessons.component.html',
  styleUrls: ['./driving-lessons.component.css']
})
/**
 * Panel zarządzania jazdami szkoleniowymi.
 */
export class DrivingLessonsComponent implements OnInit {

  waitingLessons: DrivingLesson[] = [];
  passedLessons: DrivingLesson[] = [];
  failedLessons: DrivingLesson[] = [];

  instructors: Employee[] = [];
  instructor: Employee | null = null;
  startTime: Date | null = null;
  endTime: Date | null = null;

  feedback: string = "";

  constructor(private authService: AuthService,
              private drivingLessonService: DrivingLessonService,
              private employeeService: EmployeeService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllInstructorsByCourseCategory(email);
    this.getAllDrivingLessons(email);
  }

  private getAllInstructorsByCourseCategory(email: string) {
    this.courseService.findActiveCourseByEmail(email).subscribe(course => {
      if (course != null && course.licenseCategory != null) {
        const role = EmployeeRole.getInstructorRoleByCourseCategory(course.licenseCategory);
        this.employeeService.findAllByRole(role).subscribe(
          employees => this.instructors = employees)
      }
    });
  }

  private getAllDrivingLessons(email: string) {
    this.drivingLessonService.findAllByEmail(email)
      .subscribe(lessons => this.sortLessonsByStatus(lessons));
  }

  private sortLessonsByStatus(lessons: DrivingLesson[]): void {
    lessons.forEach(lesson => {
      const lessonStatus = lesson.lessonStatus;
      if (lessonStatus != null) {
        switch (LessonStatus[lessonStatus] as unknown) {
          case LessonStatus.REQUESTED:
          case LessonStatus.ACCEPTED:
            this.waitingLessons.push(lesson);
            break;
          case LessonStatus.PASSED:
            this.passedLessons.push(lesson);
            break;
          case LessonStatus.REJECTED:
          case LessonStatus.FAILED:
            this.failedLessons.push(lesson);
            break;
          default:
            break;
        }
      }
    });
    this.sortLessonsByStartTime(this.waitingLessons);
    this.sortLessonsByStartTime(this.passedLessons);
    this.sortLessonsByStartTime(this.failedLessons);
  }

  private sortLessonsByStartTime(lessons: DrivingLesson[]): void {
    lessons.sort((a, b) =>
      new Date(a.startTime).getTime() - new Date(b.startTime).getTime());
  }

  public convertEmployee(employee: Employee | null): string {
    if (employee != null) {
      return employee.fullName.concat(" (")
        .concat(employee.email).concat(")");
    }
    return "";
  }

  public translateStatus(status: LessonStatus | null): string {
    if (status != null) {
      return LessonStatus.translate(status);
    }
    return "";
  }

  public convertDate(date: Date): string {
    const dateToParse = new Date(date);
    return dateToParse.toLocaleDateString().concat("  ")
      .concat(dateToParse.toLocaleTimeString());
  }

  public request(): void {
    // @ts-ignore
    if (this.instructor != null && this.instructor != "" && this.startTime != null && this.endTime != null) {
      const studentEmail = this.authService.getUserEmail();
      // @ts-ignore
      const employeeEmail = this.instructor.email;
      const lesson = new DrivingLesson(null, null, null, this.startTime, this.endTime);
      this.drivingLessonService.addLesson(studentEmail, employeeEmail, lesson).subscribe(
        () => {
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
    this.waitingLessons = [];
    this.passedLessons = [];
    this.failedLessons = [];
    this.instructors = [];
    this.instructor = null;
    this.startTime = null;
    this.endTime = null;
    this.feedback = "";
  }

}
