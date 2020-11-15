import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

export class User {
  constructor(
    public login: string,
    public name: string,
    public password: string,
    public email: string,
  ) {
  }
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  login = '';
  name = '';
  password = '';
  email = '';

  constructor(private router: Router) {}

  ngOnInit() {}

  register() {
    let user: User = {
      login: this.login,
      name: this.name,
      password: this.password,
      email: this.email
    };

    this.router.navigate(['login']);

    // (this.httpclientservice.createEmployee(user).subscribe(
    //     data => {
    //       console.log(true)
    //       this.router.navigate(['login']);
    //       this.invalidLogin = false
    //     },
    //     error => {
    //       this.invalidLogin = true
    //     }
    //   )
    // );
  }

}
