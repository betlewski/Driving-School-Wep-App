import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {CourseService} from "../../../../service/rest/course/course.service";
import {CourseStatus} from "../../../../utils/course-status";
import {Router} from "@angular/router";
import {TheoryLessonsService} from "../../../../service/rest/theory-lessons/theory-lessons.service";

@Component({
  selector: 'app-home-student',
  templateUrl: './home-student.component.html',
  styleUrls: ['./home-student.component.css']
})
/**
 * Panel główny kursanta
 */
export class HomeStudentComponent implements OnInit {

  email = "";
  isActiveCourse = false;
  theoryEnable = false;
  theoryActive = false;
  drivingEnable = false;
  examEnable = false;

  constructor(private router: Router,
              private authService: AuthService,
              private courseService: CourseService,
              private theoryLessonsService: TheoryLessonsService) {
  }

  ngOnInit(): void {
    this.email = this.authService.getUserEmail();
    this.initDashboardTabs();
    this.checkIfActiveTheoryExist();
  }

  private initDashboardTabs() {
    this.courseService.findActiveCourseByEmail(this.email)
      .subscribe(course => {
        if (course != null && course.courseStatus != null) {
          this.isActiveCourse = true;
          this.router.navigate(["/home/student/course"]);
          switch (CourseStatus[course.courseStatus] as unknown) {
            case CourseStatus.LECTURES:
              this.setDashboardTabs(true, false, false);
              break;
            case CourseStatus.THEORY_INTERNAL_EXAM:
              this.setDashboardTabs(true, false, true);
              break;
            case CourseStatus.DRIVING_LESSONS:
            case CourseStatus.PRACTICAL_INTERNAL_EXAM:
            case CourseStatus.STATE_EXAMS:
            case CourseStatus.FINISHED:
              this.setDashboardTabs(true, true, true);
              break;
          }
        } else {
          this.router.navigate(["/home/student/course/init"]);
        }
      });
  }

  private setDashboardTabs(theoryEnable: boolean, drivingEnable: boolean, examEnable: boolean) {
    this.theoryEnable = theoryEnable;
    this.drivingEnable = drivingEnable;
    this.examEnable = examEnable;
  }

  private checkIfActiveTheoryExist() {
    this.theoryLessonsService.isActiveByStudent(this.email)
      .subscribe(isActive => this.theoryActive = isActive);
  }

  public logout() {
    this.authService.clearItems();
    this.router.navigate(["/login"]);
  }

}
