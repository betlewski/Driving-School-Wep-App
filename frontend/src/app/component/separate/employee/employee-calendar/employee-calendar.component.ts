import {Component, OnInit} from '@angular/core';
import {CalendarComponent} from "../../../common/calendar/calendar.component";
import {UserRole} from "../../../../utils/user-role";

@Component({
  selector: 'app-employee-calendar',
  templateUrl: './employee-calendar.component.html',
  styleUrls: ['./employee-calendar.component.css']
})
/**
 * Kalendarz z zajęciami przypisanymi do pracownika.
 * (wykłady, jazdy, egzaminy)
 */
export class EmployeeCalendarComponent extends CalendarComponent implements OnInit {

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    const userRole = this.authService.getUserRole();
    if (userRole == UserRole.LECTURER) {
      this.getAllEventsForLecturer(email);
    } else if (userRole == UserRole.INSTRUCTOR) {
      this.getAllEventsForInstructor(email);
    }
  }

}
