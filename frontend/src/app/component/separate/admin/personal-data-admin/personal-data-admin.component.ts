import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {EmployeeService} from "../../../../service/rest/employee/employee.service";
import {Utils} from "../../../../utils/utils";
import {Employee} from "../../../../model/employee.model";

@Component({
  selector: 'app-personal-data-admin',
  templateUrl: './personal-data-admin.component.html',
  styleUrls: ['./personal-data-admin.component.css']
})
/**
 * Panel danych osobowych administratora.
 */
export class PersonalDataAdminComponent implements OnInit {

  name: string | null = "";
  phoneNumber: string | null = "";
  registrationDate: string | null = "";

  viewMode = true;

  constructor(private authService: AuthService,
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
      this.phoneNumber = employee.phoneNumber;
      this.registrationDate = employee.registrationDate;
    }
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
