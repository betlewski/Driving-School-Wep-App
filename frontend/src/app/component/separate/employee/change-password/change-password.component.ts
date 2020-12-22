import {Component, OnInit} from '@angular/core';
import {Utils} from "../../../../utils/utils";
import {EmployeeService} from "../../../../service/rest/employee/employee.service";
import {TextConstants} from "../../../../utils/text-constants";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
/**
 * Panel zmiany hasła pracownika (wykładowcy/instruktora).
 */
export class ChangePasswordComponent implements OnInit {

  oldPassword: string = "";
  newPassword1: string = "";
  newPassword2: string = "";
  feedback: string = "";

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
  }

  public changePwd() {
    if (this.checkIfDataNotEmpty()) {
      if (this.checkIfNewPasswordsEquals()) {
        this.employeeService.changePassword(this.oldPassword, this.newPassword1)
          .subscribe(() => {
            this.feedback = TextConstants.CHANGE_PWD_SUCCESSFUL;
            this.cleanData();
          }, error => {
            this.setFeedbackIfError(error);
          })
      } else {
        this.feedback = TextConstants.CHANGE_PWD_NOT_EQUALS_NEW;
      }
    } else {
      this.feedback = TextConstants.CHANGE_PWD_INCOMPLETE_DATA;
    }
  }

  private checkIfDataNotEmpty(): boolean {
    return Utils.checkStringIfNotEmpty(this.oldPassword)
      && Utils.checkStringIfNotEmpty(this.newPassword1)
      && Utils.checkStringIfNotEmpty(this.newPassword2);
  }

  private checkIfNewPasswordsEquals(): boolean {
    return this.newPassword1 == this.newPassword2;
  }

  private cleanData(): void {
    this.oldPassword = "";
    this.newPassword1 = "";
    this.newPassword2 = "";
  }

  private setFeedbackIfError(error: any): void {
    if (error.status == 403) {
      this.feedback = TextConstants.CHANGE_PWD_INVALID_OLD;
    } else {
      this.feedback = TextConstants.CHANGE_PWD_WEAK_NEW;
    }
  }

}
