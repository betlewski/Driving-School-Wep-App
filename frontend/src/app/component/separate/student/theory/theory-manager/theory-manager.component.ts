import {Component, OnInit} from '@angular/core';
import {TheoryLessonsService} from "../../../../../service/rest/theory-lessons/theory-lessons.service";
import {AuthService} from "../../../../../service/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-theory-manager',
  templateUrl: './theory-manager.component.html',
  styleUrls: ['./theory-manager.component.css']
})
/**
 * Menadżer do przekierowania kursanta na odpowiedni panel dot. wykładów.
 * (w zależności od przypisania i statusu cyklu wykładów)
 */
export class TheoryManagerComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService,
              private theoryLessonsService: TheoryLessonsService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.checkActiveTheoryAndRoute(email);
  }

  private checkActiveTheoryAndRoute(email: string) {
    this.theoryLessonsService.isActiveByEmail(email)
      .subscribe(isActive => {
        if (isActive) {
          this.router.navigate(["/lessons"]);
        } else {
          this.router.navigate(["/init"]);
        }
      })
  }

}
