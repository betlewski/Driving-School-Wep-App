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
        children: []
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
