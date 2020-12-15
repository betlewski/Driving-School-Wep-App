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
    CryptoJsService,
    StudentService,
    CourseService,
    DocumentService,
    PaymentService,
    DrivingLessonService,
    InternalExamService,
    TheoryLessonsService,
    EmployeeService,
    LectureSeriesService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
