import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css',
    '../login/login.component.css']
})
/**
 * Komponent rejestracji użytkowników
 */
export class RegistrationComponent implements OnInit {

  fullName: string = "";
  birthDate: string = "";
  email: string = "";
  password: string = "";
  feedback: string = "";

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  register() {
    this.feedback = "Podane dane: " + this.fullName + " " +
      this.birthDate + " " + this.email + " " + this.password;
    // this.router.navigate(['login']);

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
