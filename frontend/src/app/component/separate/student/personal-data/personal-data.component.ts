import {Component, OnInit} from '@angular/core';
import {StudentService} from "../../../../service/rest/student/student.service";
import {AuthService} from "../../../../service/auth/auth.service";
import {Student} from "../../../../model/student.model";
import {Utils} from "../../../../utils/utils";

@Component({
  selector: 'app-personal-data',
  templateUrl: './personal-data.component.html',
  styleUrls: ['./personal-data.component.css']
})
/**
 * Panel danych osobowych kursanta
 */
export class PersonalDataComponent implements OnInit {

  name: string | null = "";
  birthDate: string | null = "";
  address: string | null = "";
  phoneNumber: string | null = "";
  pkk: string | null = "";
  registrationDate: string | null = "";

  viewMode = true;

  constructor(private authService: AuthService,
              private studentService: StudentService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.studentService.findByEmail(email)
      .subscribe(student =>
        this.fillFormDataFromStudent(student));
  }

  private fillFormDataFromStudent(student: Student) {
    if (student != null) {
      this.name = student.fullName;
      this.birthDate = student.birthDate;
      this.address = student.address;
      this.phoneNumber = student.phoneNumber;
      this.pkk = student.pkk;
      this.registrationDate = student.registrationDate;
    }
  }

  public editData() {
    this.viewMode = false;
  }

  public saveData() {
    if (this.checkIfDataNotEmpty()) {
      const email = this.authService.getUserEmail();
      const newStudent = Student.edit(this.name, this.address, this.phoneNumber);
      this.studentService.edit(email, newStudent)
        .subscribe(() => this.viewMode = true);
    }
  }

  private checkIfDataNotEmpty(): boolean {
    return Utils.checkStringIfNotEmpty(this.name);
  }

}
