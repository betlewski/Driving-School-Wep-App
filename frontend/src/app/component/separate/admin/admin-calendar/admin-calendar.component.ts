import {Component, OnInit} from '@angular/core';
import {CalendarComponent} from "../../../common/calendar/calendar.component";
import {Employee} from "../../../../model/employee.model";
import {Student} from "../../../../model/student.model";

@Component({
  selector: 'app-admin-calendar',
  templateUrl: './admin-calendar.component.html',
  styleUrls: ['./admin-calendar.component.css']
})
/**
 * Kalendarz z zajęciami przypisanymi do kursantów i pracowników.
 */
export class AdminCalendarComponent extends CalendarComponent implements OnInit {

  employees: Employee[] = [];
  chosenEmployee: Employee | null = null;
  students: Student[] = [];
  chosenStudent: Student | null = null;

  ngOnInit(): void {
    this.findAllEmployees();
    this.findAllStudents();
  }

  private findAllEmployees() {
    this.employeeService.findAllNotAdmins().subscribe(
      employees => {
        this.employees = employees;
        this.employees = this.employees.sort(
          // @ts-ignore
          (a, b) => a.fullName?.localeCompare(b.fullName));
      });
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

  public changeEmployee() {
    this.events = [];
    this.chosenStudent = null;
    // @ts-ignore
    if (this.chosenEmployee != null && this.chosenEmployee != ""
      && this.chosenEmployee.email != null) {
      this.getAllEventsForEmployee(this.chosenEmployee.email);
    }
  }

  public changeStudent() {
    this.events = [];
    this.chosenEmployee = null;
    // @ts-ignore
    if (this.chosenStudent != null && this.chosenStudent != ""
      && this.chosenStudent.email != null) {
      this.getAllEventsForStudent(this.chosenStudent.email);
    }
  }

}
