import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Student} from "../../../model/student.model";
import {Utils} from "../../../utils/utils";
import {StudentService} from "../../../service/rest/student/student.service";
import {AuthService} from "../../../service/auth/auth.service";

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
              this.router.navigate(["/home"]);
            }, () => {
              this.router.navigate(["/login"]);
            });
        },
        error => {
          if (error.status == 409) {
            this.feedback = "Podany adres email już istnieje.";
          } else {
            this.feedback = "Podano niewłaściwe dane.";
          }
        });
    }
  }

  private checkIfDataCorrect(): boolean {
    if (!Utils.checkStringIfNotEmpty(this.fullName) || !Utils.checkStringIfNotEmpty(this.birthDate)) {
      this.feedback = "Należy uzupełnić wszystkie dane.";
      return false;
    } else if (!Utils.checkIfEmailCorrect(this.email)) {
      this.feedback = "Podany adres email jest niewłaściwy.";
      return false;
    } else if (!Utils.checkIfPasswordCorrect(this.password)) {
      this.feedback = "Podane hasło jest za słabe.";
      return false;
    }
    return true;
  }

}
