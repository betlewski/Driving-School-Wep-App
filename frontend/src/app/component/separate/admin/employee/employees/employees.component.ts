import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../../service/auth/auth.service";
import {Utils} from "../../../../../utils/utils";
import {EmployeeService} from "../../../../../service/rest/employee/employee.service";
import {Employee} from "../../../../../model/employee.model";
import {EmployeeRole} from "../../../../../utils/employee-role";
import {Router} from "@angular/router";

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
/**
 * Panel zarządzania pracownikami szkoły jazdy.
 */
export class EmployeesComponent implements OnInit {

  name: string | null = "";
  email: string | null = "";
  role: string | null = "";
  phoneNumber: string | null = "";
  registrationDate: string | null = "";

  employees: Employee[] = [];
  employeeRoles: EmployeeRole[] = [];
  employeeRoleUtils = EmployeeRole;
  chosenEmployee: Employee | null = null;

  viewMode = true;

  constructor(private router: Router,
              private authService: AuthService,
              private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.findAllEmployees();
    this.fillEmployeeRoles();
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

  private fillEmployeeRoles(): void {
    this.employeeRoles = EmployeeRole.values();
  }

  public changeEmployee() {
    // @ts-ignore
    if (this.chosenEmployee != null && this.chosenEmployee != "") {
      this.name = this.chosenEmployee.fullName;
      this.email = this.chosenEmployee.email;
      // @ts-ignore
      this.role = EmployeeRole[this.chosenEmployee.employeeRole];
      this.phoneNumber = this.chosenEmployee.phoneNumber;
      this.registrationDate = this.chosenEmployee.registrationDate;
    } else {
      this.cleanData();
    }
  }

  private cleanData() {
    this.name = null;
    this.email = null;
    this.role = null;
    this.phoneNumber = null;
    this.registrationDate = null;
    this.chosenEmployee = null;
  }

  public editData() {
    this.viewMode = false;
  }

  public saveData() {
    if (this.checkIfDataNotEmpty()) {
      const currentEmail = this.getCurrentEmail();
      // @ts-ignore
      const newEmployee = new Employee(null, EmployeeRole[this.role], this.name,
        this.phoneNumber, this.email, null, this.registrationDate);
      this.employeeService.editFull(currentEmail, newEmployee)
        .subscribe(() => {
          this.findAllEmployees();
          this.cleanData();
          this.viewMode = true;
        });
    }
  }

  private checkIfDataNotEmpty(): boolean {
    return Utils.checkStringIfNotEmpty(this.name) &&
      Utils.checkStringIfNotEmpty(this.email) &&
      Utils.checkStringIfNotEmpty(this.registrationDate) &&
      this.role !== null && this.role !== "";
  }

  private getCurrentEmail(): string {
    const currentEmail = this.chosenEmployee?.email;
    return currentEmail != null ? currentEmail : "";
  }

  public addEmployee() {
    this.router.navigate(["/home/admin/employee/new"]);
  }

}
