import {Component, ChangeDetectionStrategy, ViewChild, TemplateRef} from '@angular/core';
import {CalendarEvent, CalendarView,} from 'angular-calendar';
import {isSameDay, isSameMonth} from 'date-fns';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

/**
 * Kolory wykorzystywane do oznaczania statusu wydarzeń:
 * -> żółty (wydarzenie planowane lub zatwierdzone)
 * -> zielony (wydarzenie odbyte, zakończone pomyślnie)
 * -> czerwone (wydarzenie nieodbyte lub zakończone niepomyślnie)
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

  public constructor(private modal: NgbModal) {
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
