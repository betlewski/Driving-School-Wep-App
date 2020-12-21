import {Component, ChangeDetectionStrategy, ViewChild, TemplateRef} from '@angular/core';
import {CalendarEvent, CalendarView,} from 'angular-calendar';
import {isSameDay, isSameMonth} from 'date-fns';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AuthService} from "../../../service/auth/auth.service";
import {TheoryLessonsService} from "../../../service/rest/theory-lessons/theory-lessons.service";
import {DrivingLessonService} from "../../../service/rest/driving-lesson/driving-lesson.service";
import {InternalExamService} from "../../../service/rest/internal-exam/internal-exam.service";
import {EmployeeService} from "../../../service/rest/employee/employee.service";
import {StudentService} from "../../../service/rest/student/student.service";
import {LectureSeriesService} from "../../../service/rest/lecture-series/lecture-series.service";

/**
 * Kolory wykorzystywane do oznaczania typu wydarzeń:
 * -> żółty (zajęcia teoretyczne - wykłady)
 * -> zielony (zajęcia praktyczne - jazdy szkoleniowe)
 * -> czerwone (egzaminy wewnętrzne)
 */
export const colors: any = {
  yellow: {
    primary: '#e3bc08',
    secondary: '#fefae6',
  },
  green: {
    primary: '#00cc00',
    secondary: '#ccffcc',
  },
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  }
};

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
/**
 * Szablon kalendarza stosowany w panelach różnych użytkowników.
 */
export class CalendarComponent {

  public constructor(private modal: NgbModal,
                     protected authService: AuthService,
                     protected theoryLessonsService: TheoryLessonsService,
                     protected drivingLessonService: DrivingLessonService,
                     protected internalExamService: InternalExamService,
                     protected lectureSeriesService: LectureSeriesService,
                     protected employeeService: EmployeeService,
                     protected studentService: StudentService) {
  }

  @ViewChild('modalContent', {static: true}) modalContent: TemplateRef<any> | undefined;

  CalendarView = CalendarView;
  view: CalendarView = CalendarView.Week;
  viewDate: Date = new Date();
  activeDayIsOpen: boolean = true;
  todayOpened: boolean = true;
  events: CalendarEvent[] = [];
  event: CalendarEvent | undefined;

  dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      this.activeDayIsOpen = !((isSameDay(this.viewDate, date) && this.activeDayIsOpen) || events.length === 0);
      this.viewDate = date;
    }
  }

  handleEvent(event: CalendarEvent): void {
    this.event = event;
    this.modal.open(this.modalContent, {size: 'lg', centered: true});
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
    this.todayOpened = false;
  }

  openToday() {
    this.todayOpened = true;
  }

}
