import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {Router} from "@angular/router";
import {UserRole} from "../../../../utils/user-role";

@Component({
  selector: 'app-home-employee',
  templateUrl: './home-employee.component.html',
  styleUrls: ['./home-employee.component.css']
})
/**
 * Panel główny pracownika.
 */
export class HomeEmployeeComponent implements OnInit {

  email = "";
  lecturerLogged: boolean = false;
  instructorLogged: boolean = false;

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.email = this.authService.getUserEmail();
    this.setUserLogged();
    this.router.navigate(["/home/employee/calendar"]);
  }

  private setUserLogged(): void {
    const userRole = this.authService.getUserRole();
    if (userRole == UserRole.LECTURER) {
      this.lecturerLogged = true;
    } else if (userRole == UserRole.INSTRUCTOR) {
      this.instructorLogged = true;
    }
  }

  public logout() {
    this.authService.clearItems();
    this.router.navigate(["/login"]);
  }

}
