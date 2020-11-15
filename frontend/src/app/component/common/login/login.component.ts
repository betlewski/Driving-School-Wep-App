import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
/**
 * Logowanie użytkowników (widok startowy)
 */
export class LoginComponent implements OnInit {

  login = '';
  password = '';

  constructor(private router: Router) {}

  ngOnInit() {}

  checkLogin() {
    this.router.navigate(['register']);
  }

}
