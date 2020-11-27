import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
/**
 * Panel główny administratora
 */
export class HomeAdminComponent implements OnInit {

  email = "";

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.email = this.authService.getUserEmail();
  }

}
