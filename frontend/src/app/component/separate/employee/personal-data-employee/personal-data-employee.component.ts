import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {EmployeeService} from "../../../../service/rest/employee/employee.service";
import {Employee} from "../../../../model/employee.model";
import {Utils} from "../../../../utils/utils";
import {EmployeeRole} from "../../../../utils/employee-role";
import {Router} from "@angular/router";

@Component({
  selector: 'app-personal-data-employee',
  templateUrl: './personal-data-employee.component.html',
  styleUrls: ['./personal-data-employee.component.css']
})
/**
 * Panel danych osobowych pracownika (wykładowcy/instruktora).
 */
export class PersonalDataEmployeeComponent implements OnInit {

  name: string | null = "";
  role: string | null = "";
  phoneNumber: string | null = "";
  registrationDate: string | null = "";

  viewMode = true;

  constructor(private router: Router,
              private authService: AuthService,
              private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.employeeService.findByEmail(email)
      .subscribe(employee =>
        this.fillFormDataFromEmployee(employee));
  }

  private fillFormDataFromEmployee(employee: Employee) {
    if (employee != null) {
      this.name = employee.fullName;
      // @ts-ignore
      this.role = EmployeeRole.translate(EmployeeRole[employee.employeeRole]);
      this.phoneNumber = employee.phoneNumber;
      this.registrationDate = employee.registrationDate;
    }
  }

  public changePwd() {
    this.router.navigate(["/home/employee/data/password"]).then();
  }

  public editData() {
    this.viewMode = false;
  }

  public saveData() {
    if (this.checkIfDataNotEmpty()) {
      const email = this.authService.getUserEmail();
      const newEmployee = Employee.edit(this.name, this.phoneNumber);
      this.employeeService.edit(email, newEmployee)
        .subscribe(() => this.viewMode = true);
    }
  }

  private checkIfDataNotEmpty(): boolean {
    return Utils.checkStringIfNotEmpty(this.name);
  }

}
