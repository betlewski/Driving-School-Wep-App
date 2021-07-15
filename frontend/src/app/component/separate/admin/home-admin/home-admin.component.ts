import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../service/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
/**
 * Panel główny administratora.
 */
export class HomeAdminComponent implements OnInit {

  email = "";

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.email = this.authService.getUserEmail();
    this.router.navigate(["/home/admin/course"]);
  }

  public logout() {
    this.authService.clearItems();
    this.router.navigate(["/login"]);
  }

}
