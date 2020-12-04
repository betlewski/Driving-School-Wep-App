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
import {CalendarComponent} from './component/separate/student/calendar/calendar.component';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CalendarModule.forRoot({provide: DateAdapter, useFactory: adapterFactory})
  ],
  providers: [
    AuthService,
    CryptoJsService,
    StudentService,
    CourseService,
    DocumentService,
    PaymentService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
