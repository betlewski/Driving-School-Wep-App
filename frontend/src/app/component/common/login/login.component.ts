import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {JwtRequest} from "../../../model/jwt-request.model";
import {Utils} from "../../../util/utils";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
/**
 * Komponent logowania użytkowników
 */
export class LoginComponent implements OnInit {

  email: string = "";
  password: string = "";

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  checkLogin() {
    if (Utils.checkStringIfNotEmpty(this.email) && Utils.checkStringIfNotEmpty(this.password)) {
      const jwtRequest = new JwtRequest(this.email, this.password);
      this.router.navigate([jwtRequest]);
    }
  }

}
