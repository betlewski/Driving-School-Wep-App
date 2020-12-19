import {Component, OnInit} from '@angular/core';
import {Employee} from "../../../../../model/employee.model";
import {EmployeeRole} from "../../../../../utils/employee-role";
import {Router} from "@angular/router";
import {AuthService} from "../../../../../service/auth/auth.service";
import {EmployeeService} from "../../../../../service/rest/employee/employee.service";
import {Utils} from "../../../../../utils/utils";
import {TextConstants} from "../../../../../utils/text-constants";

@Component({
  selector: 'app-employee-new',
  templateUrl: './employee-new.component.html',
  styleUrls: ['./employee-new.component.css']
})
/**
 * Panel dodawania pracowników szkoły jazdy.
 */
export class EmployeeNewComponent implements OnInit {

  name: string = "";
  role: string = "";
  email: string = "";
  password: string = "";

  employeeRoles: EmployeeRole[] = [];
  employeeRoleUtils = EmployeeRole;
  feedback: string = "";

  constructor(private router: Router,
              private authService: AuthService,
              private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.fillEmployeeRoles();
  }

  private fillEmployeeRoles(): void {
    this.employeeRoles = EmployeeRole.values();
  }

  public add() {
    if (this.checkIfDataCorrect()) {
      // @ts-ignore
      const employee = Employee.register(this.name, EmployeeRole[this.role], this.email, this.password);
      this.employeeService.add(employee).subscribe(() => {
          this.router.navigate(["/home/admin/employee"]).then();
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
    if (!Utils.checkStringIfNotEmpty(this.name) || this.role === null || this.role === "") {
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
