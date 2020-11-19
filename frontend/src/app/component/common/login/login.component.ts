import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Utils} from "../../../utils/utils";
import {AuthService} from "../../../service/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
/**
 * Komponent logowania użytkowników
 */
export class LoginComponent implements OnInit {

  private email = "";
  private password = "";

  constructor(private router: Router,
              private authService: AuthService) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(["/home"]);
    }
  }

  ngOnInit() {
  }

  public login() {
    if (Utils.checkStringIfNotEmpty(this.email) && Utils.checkStringIfNotEmpty(this.password)) {
      this.authService.authenticate(this.email, this.password)
        .subscribe(() => {
          this.router.navigate(["/home"]);
        });
    }
  }

}
