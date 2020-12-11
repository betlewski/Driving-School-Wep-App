import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Student} from "../../../model/student.model";
import {Utils} from "../../../utils/utils";
import {StudentService} from "../../../service/rest/student/student.service";
import {AuthService} from "../../../service/auth/auth.service";
import {TextConstants} from "../../../utils/text-constants";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css',
    '../login/login.component.css']
})
/**
 * Komponent rejestracji kursantów
 */
export class RegistrationComponent implements OnInit {

  public fullName = "";
  public birthDate = "";
  public email = "";
  public password = "";
  public feedback = "";

  constructor(private router: Router,
              private authService: AuthService,
              private studentService: StudentService) {
  }

  ngOnInit() {
  }

  public register() {
    if (this.checkIfDataCorrect()) {
      const student = Student.register(this.fullName, this.birthDate, this.email, this.password);
      this.studentService.register(student).subscribe(
        () => {
          this.authService.authenticate(this.email, this.password)
            .subscribe(() => {
              this.router.navigate(["/home/student"]);
            }, () => {
              this.router.navigate(["/login"]);
            });
        },
        error => {
          if (error.status == 409) {
            this.feedback = TextConstants.REGISTRATION_DUPLICATED_EMAIL;
          } else {
            this.feedback = TextConstants.REGISTRATION_INVALID_DATA;
          }
        });
    }
  }

  private checkIfDataCorrect(): boolean {
    if (!Utils.checkStringIfNotEmpty(this.fullName) || !Utils.checkStringIfNotEmpty(this.birthDate)) {
      this.feedback = TextConstants.REGISTRATION_INCOMPLETE_DATA;
      return false;
    } else if (!Utils.checkIfEmailCorrect(this.email)) {
      this.feedback = TextConstants.REGISTRATION_INVALID_EMAIL;
      return false;
    } else if (!Utils.checkIfPasswordCorrect(this.password)) {
      this.feedback = TextConstants.REGISTRATION_WEAK_PASSWORD;
      return false;
    }
    return true;
  }

}
