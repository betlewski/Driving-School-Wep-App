import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from "./component/common/login/login.component";
import {RegistrationComponent} from "./component/common/registration/registration.component";
import {HomeStudentComponent} from "./component/separate/student/home/home-student.component";
import {HomeEmployeeComponent} from "./component/separate/employee/home-employee/home-employee.component";
import {HomeAdminComponent} from "./component/separate/admin/home-admin/home-admin.component";
import {CourseComponent} from "./component/separate/student/course/course.component";
import {CourseInitComponent} from "./component/separate/student/course-init/course-init.component";
import {PersonalDataComponent} from "./component/separate/student/personal-data/personal-data.component";
import {OfficialComponent} from "./component/separate/student/official/official.component";
import {StudentCalendarComponent} from "./component/separate/student/calendar/student-calendar.component";
import {DrivingLessonsComponent} from "./component/separate/student/driving-lessons/driving-lessons.component";
import {ExamComponent} from "./component/separate/student/exam/exam.component";
import {TheoryInitComponent} from "./component/separate/student/theory/theory-init/theory-init.component";
import {TheoryLessonsComponent} from "./component/separate/student/theory/theory-lessons/theory-lessons.component";
import {PersonalDataAdminComponent} from './component/separate/admin/personal-data-admin/personal-data-admin.component';
import {StudentsComponent} from "./component/separate/admin/students/students.component";
import {OfficialAdminComponent} from "./component/separate/admin/official-admin/official-admin.component";
import {CourseAdminComponent} from "./component/separate/admin/course-admin/course-admin.component";
import {EmployeesComponent} from "./component/separate/admin/employee/employees/employees.component";
import {EmployeeNewComponent} from "./component/separate/admin/employee/employee-new/employee-new.component";
import {AdminCalendarComponent} from "./component/separate/admin/admin-calendar/admin-calendar.component";
import {PersonalDataEmployeeComponent} from "./component/separate/employee/personal-data-employee/personal-data-employee.component";
import {ChangePasswordComponent} from "./component/separate/employee/change-password/change-password.component";
import {EmployeeCalendarComponent} from "./component/separate/employee/employee-calendar/employee-calendar.component";
import {DrivingLessonsEmployeeComponent} from "./component/separate/employee/driving-lessons-employee/driving-lessons-employee.component";
import {ExamEmployeeComponent} from "./component/separate/employee/exam-employee/exam-employee.component";
import {TheoryLessonsEmployeeComponent} from "./component/separate/employee/theory-lessons-employee/theory-lessons-employee.component";
import {LectureSeriesComponent} from "./component/separate/employee/lectures/lecture-series/lecture-series.component";
import {LectureSeriesInitComponent} from "./component/separate/employee/lectures/lecture-series-init/lecture-series-init.component";
import {AuthGuardStudent} from "./service/auth-guard/student/auth-guard-student.service";
import {AuthGuardEmployee} from "./service/auth-guard/employee/auth-guard-employee.service";
import {AuthGuardAdmin} from "./service/auth-guard/admin/auth-guard-admin.service";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {
    path: 'home',
    children: [
      {
        path: 'student',
        component: HomeStudentComponent,
        canActivate: [AuthGuardStudent],
        children: [
          {
            path: 'course',
            children: [
              {
                path: '',
                component: CourseComponent,
                canActivate: [AuthGuardStudent]
              },
              {
                path: 'init',
                component: CourseInitComponent,
                canActivate: [AuthGuardStudent]
              }
            ]
          },
          {
            path: 'data',
            component: PersonalDataComponent,
            canActivate: [AuthGuardStudent]
          },
          {
            path: 'official',
            component: OfficialComponent,
            canActivate: [AuthGuardStudent]
          },
          {
            path: 'calendar',
            component: StudentCalendarComponent,
            canActivate: [AuthGuardStudent]
          },
          {
            path: 'driving',
            component: DrivingLessonsComponent,
            canActivate: [AuthGuardStudent]
          },
          {
            path: 'theory',
            component: TheoryLessonsComponent,
            canActivate: [AuthGuardStudent]
          },
          {
            path: 'theory/init',
            component: TheoryInitComponent,
            canActivate: [AuthGuardStudent]
          },
          {
            path: 'exam',
            component: ExamComponent,
            canActivate: [AuthGuardStudent]
          }
        ]
      },
      {
        path: 'employee',
        component: HomeEmployeeComponent,
        canActivate: [AuthGuardEmployee],
        children: [
          {
            path: 'data',
            component: PersonalDataEmployeeComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'data/password',
            component: ChangePasswordComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'calendar',
            component: EmployeeCalendarComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'driving',
            component: DrivingLessonsEmployeeComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'exam',
            component: ExamEmployeeComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'theory',
            component: TheoryLessonsEmployeeComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'lecture',
            component: LectureSeriesComponent,
            canActivate: [AuthGuardEmployee]
          },
          {
            path: 'lecture/init',
            component: LectureSeriesInitComponent,
            canActivate: [AuthGuardEmployee]
          }
        ]
      },
      {
        path: 'admin',
        component: HomeAdminComponent,
        canActivate: [AuthGuardAdmin],
        children: [
          {
            path: 'data',
            component: PersonalDataAdminComponent,
            canActivate: [AuthGuardAdmin]
          },
          {
            path: 'student',
            component: StudentsComponent,
            canActivate: [AuthGuardAdmin]
          },
          {
            path: 'official',
            component: OfficialAdminComponent,
            canActivate: [AuthGuardAdmin]
          },
          {
            path: 'course',
            component: CourseAdminComponent,
            canActivate: [AuthGuardAdmin]
          },
          {
            path: 'employee',
            component: EmployeesComponent,
            canActivate: [AuthGuardAdmin]
          },
          {
            path: 'employee/new',
            component: EmployeeNewComponent,
            canActivate: [AuthGuardAdmin]
          },
          {
            path: 'calendar',
            component: AdminCalendarComponent,
            canActivate: [AuthGuardAdmin]
          }
        ]
      },
    ]
  },
  {path: '**', pathMatch: 'full', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
