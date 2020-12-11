import {Component, OnInit} from '@angular/core';
import {LicenseCategory} from "../../../../utils/license-category";
import {CourseService} from "../../../../service/rest/course/course.service";
import {AuthService} from "../../../../service/auth/auth.service";
import {Router} from "@angular/router";
import {TextConstants} from "../../../../utils/text-constants";

@Component({
  selector: 'app-course-init',
  templateUrl: './course-init.component.html',
  styleUrls: ['./course-init.component.css']
})
/**
 * Panel dodawania kursu, jeśli żaden aktywny nie istnieje.
 */
export class CourseInitComponent implements OnInit {

  licenseCategories = Array<LicenseCategory>();
  feedback = "";

  constructor(private router: Router,
              private authService: AuthService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    this.licenseCategories = LicenseCategory.values();
  }

  public initCourse(categoryToInit: LicenseCategory) {
    if (categoryToInit != null) {
      const email = this.authService.getUserEmail();
      this.courseService.addCourse(email, categoryToInit)
        .subscribe(() => window.location.replace("home/student/course"),
          () => this.feedback = TextConstants.COURSE_INIT_TOO_YOUNG);
    }
  }

}
