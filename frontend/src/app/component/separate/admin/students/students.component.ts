import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {StudentService} from "../../../../service/rest/student/student.service";
import {Student} from "../../../../model/student.model";
import {Utils} from "../../../../utils/utils";

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
/**
 * Panel zarządzania kursantami.
 */
export class StudentsComponent implements OnInit {

  name: string | null = "";
  email: string | null = "";
  birthDate: string | null = "";
  address: string | null = "";
  phoneNumber: string | null = "";
  pkk: string | null = "";
  registrationDate: string | null = "";

  students: Student[] = [];
  chosenStudent: Student | null = null;

  viewMode = true;

  constructor(private authService: AuthService,
              private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.findAllStudents();
  }

  private findAllStudents() {
    this.studentService.findAll().subscribe(
      students => {
        this.students = students;
        this.students = this.students.sort(
          // @ts-ignore
          (a, b) => a.fullName?.localeCompare(b.fullName));
      });
  }

  public changeStudent() {
    // @ts-ignore
    if (this.chosenStudent != null && this.chosenStudent != "") {
      this.name = this.chosenStudent.fullName;
      this.email = this.chosenStudent.email;
      this.birthDate = this.chosenStudent.birthDate;
      this.address = this.chosenStudent.address;
      this.phoneNumber = this.chosenStudent.phoneNumber;
      this.pkk = this.chosenStudent.pkk;
      this.registrationDate = this.chosenStudent.registrationDate;
    } else {
      this.cleanData();
    }
  }

  public editData() {
    this.viewMode = false;
  }

  public saveData() {
    if (this.checkIfDataNotEmpty()) {
      const currentEmail = this.getCurrentEmail();
      const newStudent = new Student(null, this.name, this.pkk, this.birthDate,
        this.address, this.phoneNumber, this.email, null, this.registrationDate);
      this.studentService.editFull(currentEmail, newStudent)
        .subscribe(() => {
          this.findAllStudents();
          this.cleanData();
          this.viewMode = true;
        });
    }
  }

  private checkIfDataNotEmpty(): boolean {
    return Utils.checkStringIfNotEmpty(this.name) &&
      Utils.checkStringIfNotEmpty(this.email) &&
      Utils.checkStringIfNotEmpty(this.birthDate) &&
      Utils.checkStringIfNotEmpty(this.registrationDate);
  }

  private getCurrentEmail(): string {
    const currentEmail = this.chosenStudent?.email;
    return currentEmail != null ? currentEmail : "";
  }

  private cleanData() {
    this.name = null;
    this.email = null;
    this.birthDate = null;
    this.address = null;
    this.phoneNumber = null;
    this.pkk = null;
    this.registrationDate = null;
    this.chosenStudent = null;
  }

}
