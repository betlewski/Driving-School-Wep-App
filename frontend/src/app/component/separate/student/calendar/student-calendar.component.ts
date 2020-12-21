import {Component, OnInit} from '@angular/core';
import {CalendarComponent} from "../../../common/calendar/calendar.component";

@Component({
  selector: 'app-student-calendar',
  templateUrl: './student-calendar.component.html',
  styleUrls: ['./student-calendar.component.css']
})
/**
 * Kalendarz z zajęciami przypisanymi do kursanta.
 * (wykłady, jazdy, egzaminy)
 */
export class StudentCalendarComponent extends CalendarComponent implements OnInit {

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllEventsForStudent(email);
  }

}
