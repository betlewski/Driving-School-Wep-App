import {Component, OnInit} from '@angular/core';
import {CourseService} from "../../../../service/rest/course/course.service";

@Component({
  selector: 'app-course-admin',
  templateUrl: './course-admin.component.html',
  styleUrls: ['./course-admin.component.css']
})
/**
 * Panel wszystkich aktywnych kursów nauki.
 */
export class CourseAdminComponent implements OnInit {

  constructor(private courseService: CourseService) {
  }

  ngOnInit(): void {
    this.courseService.getAllReports().subscribe(
      data => console.log(data));
  }

}
