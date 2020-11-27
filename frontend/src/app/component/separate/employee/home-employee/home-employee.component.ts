import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";

@Component({
  selector: 'app-home-employee',
  templateUrl: './home-employee.component.html',
  styleUrls: ['./home-employee.component.css']
})
/**
 * Panel główny pracownika
 */
export class HomeEmployeeComponent implements OnInit {

  email = "";

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.email = this.authService.getUserEmail();
  }

}
