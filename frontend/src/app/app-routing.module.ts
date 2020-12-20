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

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {
    path: 'home',
    children: [
      {
        path: 'student',
        component: HomeStudentComponent,
        children: [
          {
            path: 'course',
            children: [
              {
                path: '',
                component: CourseComponent
              },
              {
                path: 'init',
                component: CourseInitComponent
              }
            ]
          },
          {
            path: 'data',
            component: PersonalDataComponent
          },
          {
            path: 'official',
            component: OfficialComponent
          },
          {
            path: 'calendar',
            component: StudentCalendarComponent
          },
          {
            path: 'driving',
            component: DrivingLessonsComponent
          },
          {
            path: 'theory',
            component: TheoryLessonsComponent
          },
          {
            path: 'theory/init',
            component: TheoryInitComponent
          },
          {
            path: 'exam',
            component: ExamComponent
          }
        ]
      },
      {
        path: 'employee',
        component: HomeEmployeeComponent,
        children: []
      },
      {
        path: 'admin',
        component: HomeAdminComponent,
        children: [
          {
            path: 'data',
            component: PersonalDataAdminComponent
          },
          {
            path: 'student',
            component: StudentsComponent
          },
          {
            path: 'official',
            component: OfficialAdminComponent
          },
          {
            path: 'course',
            component: CourseAdminComponent
          },
          {
            path: 'employee',
            component: EmployeesComponent
          },
          {
            path: 'employee/new',
            component: EmployeeNewComponent
          },
          {
            path: 'calendar',
            component: AdminCalendarComponent
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
