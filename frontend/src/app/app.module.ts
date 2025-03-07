import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './component/common/login/login.component';
import {RegistrationComponent} from './component/common/registration/registration.component';
import {FormsModule} from "@angular/forms";
import {CryptoJsService} from "./service/crypto-js/crypto-js.service";
import {AuthService} from "./service/auth/auth.service";
import {HttpClientModule} from "@angular/common/http";
import {HomeStudentComponent} from './component/separate/student/home/home-student.component';
import {HomeEmployeeComponent} from './component/separate/employee/home-employee/home-employee.component';
import {HomeAdminComponent} from './component/separate/admin/home-admin/home-admin.component';
import {StudentService} from "./service/rest/student/student.service";
import {CourseService} from "./service/rest/course/course.service";
import {CourseComponent} from './component/separate/student/course/course.component';
import {CourseInitComponent} from './component/separate/student/course-init/course-init.component';
import {PersonalDataComponent} from './component/separate/student/personal-data/personal-data.component';
import {OfficialComponent} from './component/separate/student/official/official.component';
import {DocumentService} from "./service/rest/document/document.service";
import {PaymentService} from "./service/rest/payment/payment.service";
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {StudentCalendarComponent} from './component/separate/student/calendar/student-calendar.component';
import {CalendarComponent} from "./component/common/calendar/calendar.component";
import {DrivingLessonService} from "./service/rest/driving-lesson/driving-lesson.service";
import {InternalExamService} from "./service/rest/internal-exam/internal-exam.service";
import {TheoryLessonsService} from "./service/rest/theory-lessons/theory-lessons.service";
import {DrivingLessonsComponent} from './component/separate/student/driving-lessons/driving-lessons.component';
import {EmployeeService} from "./service/rest/employee/employee.service";
import {ExamComponent} from './component/separate/student/exam/exam.component';
import {TheoryInitComponent} from './component/separate/student/theory/theory-init/theory-init.component';
import {TheoryLessonsComponent} from './component/separate/student/theory/theory-lessons/theory-lessons.component';
import {LectureSeriesService} from "./service/rest/lecture-series/lecture-series.service";
import {PersonalDataAdminComponent} from './component/separate/admin/personal-data-admin/personal-data-admin.component';
import {StudentsComponent} from './component/separate/admin/students/students.component';
import {OfficialAdminComponent} from './component/separate/admin/official-admin/official-admin.component';
import {CourseAdminComponent} from './component/separate/admin/course-admin/course-admin.component';
import {EmployeesComponent} from './component/separate/admin/employee/employees/employees.component';
import {EmployeeNewComponent} from './component/separate/admin/employee/employee-new/employee-new.component';
import {AdminCalendarComponent} from './component/separate/admin/admin-calendar/admin-calendar.component';
import {PersonalDataEmployeeComponent} from './component/separate/employee/personal-data-employee/personal-data-employee.component';
import {ChangePasswordComponent} from './component/separate/employee/change-password/change-password.component';
import {EmployeeCalendarComponent} from './component/separate/employee/employee-calendar/employee-calendar.component';
import {DrivingLessonsEmployeeComponent} from './component/separate/employee/driving-lessons-employee/driving-lessons-employee.component';
import {ExamEmployeeComponent} from './component/separate/employee/exam-employee/exam-employee.component';
import {TheoryLessonsEmployeeComponent} from './component/separate/employee/theory-lessons-employee/theory-lessons-employee.component';
import {LectureSeriesComponent} from "./component/separate/employee/lectures/lecture-series/lecture-series.component";
import {LectureService} from "./service/rest/lecture/lecture.service";
import {LectureSeriesInitComponent} from './component/separate/employee/lectures/lecture-series-init/lecture-series-init.component';
import {AuthGuardStudent} from "./service/auth-guard/student/auth-guard-student.service";
import {AuthGuardEmployee} from "./service/auth-guard/employee/auth-guard-employee.service";
import {AuthGuardAdmin} from "./service/auth-guard/admin/auth-guard-admin.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HomeStudentComponent,
    HomeEmployeeComponent,
    HomeAdminComponent,
    CourseComponent,
    CourseInitComponent,
    PersonalDataComponent,
    OfficialComponent,
    CalendarComponent,
    StudentCalendarComponent,
    DrivingLessonsComponent,
    ExamComponent,
    TheoryInitComponent,
    TheoryLessonsComponent,
    PersonalDataAdminComponent,
    StudentsComponent,
    OfficialAdminComponent,
    CourseAdminComponent,
    EmployeesComponent,
    EmployeeNewComponent,
    AdminCalendarComponent,
    PersonalDataEmployeeComponent,
    ChangePasswordComponent,
    EmployeeCalendarComponent,
    DrivingLessonsEmployeeComponent,
    ExamEmployeeComponent,
    TheoryLessonsEmployeeComponent,
    LectureSeriesComponent,
    LectureSeriesInitComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CalendarModule.forRoot({provide: DateAdapter, useFactory: adapterFactory}),
    NgbModule
  ],
  providers: [
    AuthService,
    AuthGuardStudent,
    AuthGuardEmployee,
    AuthGuardAdmin,
    CryptoJsService,
    StudentService,
    CourseService,
    DocumentService,
    PaymentService,
    DrivingLessonService,
    InternalExamService,
    TheoryLessonsService,
    EmployeeService,
    LectureSeriesService,
    LectureService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
