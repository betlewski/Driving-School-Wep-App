import {Component, ChangeDetectionStrategy, ViewChild, TemplateRef} from '@angular/core';
import {CalendarEvent, CalendarView,} from 'angular-calendar';
import {isSameDay, isSameMonth, parseISO} from 'date-fns';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AuthService} from "../../../service/auth/auth.service";
import {TheoryLessonsService} from "../../../service/rest/theory-lessons/theory-lessons.service";
import {DrivingLessonService} from "../../../service/rest/driving-lesson/driving-lesson.service";
import {InternalExamService} from "../../../service/rest/internal-exam/internal-exam.service";
import {EmployeeService} from "../../../service/rest/employee/employee.service";
import {StudentService} from "../../../service/rest/student/student.service";
import {LectureSeriesService} from "../../../service/rest/lecture-series/lecture-series.service";
import {Lecture} from "../../../model/lecture.model";
import {DrivingLesson} from "../../../model/driving-lesson.model";
import {Student} from "../../../model/student.model";
import {LessonStatus} from "../../../utils/lesson-status";
import {InternalExam} from "../../../model/internal-exam.model";
import {ExamType} from "../../../utils/exam-type";

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

  /**
   * Zmienne i metody oryginalne wykorzystywane w specyfice działania kalendarza:
   */
  public constructor(private modal: NgbModal,
                     protected authService: AuthService,
                     private theoryLessonsService: TheoryLessonsService,
                     private drivingLessonService: DrivingLessonService,
                     private internalExamService: InternalExamService,
                     private lectureSeriesService: LectureSeriesService,
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

  /**
   * Zmienne własne wykorzystywaniu przy pobieraniu
   * i wyświetlaniu danych w kalendarzu:
   */
  private lectureColor = colors.yellow;
  private drivingLessonColor = colors.green;
  private internalExamColor = colors.red;

  private email: string = "";

  /**
   * Metody własne do pobierania danych
   * wyświetlanych w kalendarzu pracownika:
   */
  protected getAllEventsForEmployee(email: string) {
    this.email = email;
    this.getAllLecturesForEmployee();
    this.getAllDrivingLessonsForEmployee();
    this.getAllInternalExamsForEmployee();
  }

  private getAllLecturesForEmployee() {
    this.lectureSeriesService.findAllLecturesByEmployee(this.email)
      .subscribe(lectures => {
        lectures.forEach(lecture => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromLecture(lecture),
              start: parseISO(lecture.startTime.toString()),
              end: parseISO(lecture.endTime.toString()),
              color: this.lectureColor
            }
          ];
        });
      });
  }

  private getAllDrivingLessonsForEmployee() {
    this.drivingLessonService.findAllActualByEmployee(this.email)
      .subscribe(lessons => {
        lessons.forEach(restLesson => {
          const student = restLesson.student;
          const lesson = restLesson.drivingLesson;
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromDrivingLessonForEmployee(lesson, student),
              start: parseISO(lesson.startTime.toString()),
              end: parseISO(lesson.endTime.toString()),
              color: this.drivingLessonColor
            }
          ];
        })
      });
  }

  private getAllInternalExamsForEmployee() {
    this.internalExamService.findAllActualByEmployee(this.email)
      .subscribe(exams => {
        exams.forEach(restExam => {
          const student = restExam.student;
          const exam = restExam.internalExam;
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromInternalExamForEmployee(exam, student),
              start: parseISO(exam.startTime.toString()),
              end: parseISO(exam.endTime.toString()),
              color: this.internalExamColor
            }
          ];
        });
      });
  }

  /**
   * Metody własne do pobierania danych
   * wyświetlanych w kalendarzu kursanta:
   */
  protected getAllEventsForStudent(email: string) {
    this.email = email;
    this.getAllLecturesForStudent();
    this.getAllDrivingLessonsForStudent();
    this.getAllInternalExamsForStudent();
  }

  private getAllLecturesForStudent() {
    this.theoryLessonsService.findAllLecturesByEmail(this.email)
      .subscribe(lectures => {
        lectures.forEach(lecture => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromLecture(lecture),
              start: parseISO(lecture.startTime.toString()),
              end: parseISO(lecture.endTime.toString()),
              color: this.lectureColor
            }
          ];
        });
      });
  }

  private getAllDrivingLessonsForStudent() {
    this.drivingLessonService.findAllActualByStudent(this.email)
      .subscribe(lessons => {
        lessons.forEach(lesson => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromDrivingLessonForStudent(lesson),
              start: parseISO(lesson.startTime.toString()),
              end: parseISO(lesson.endTime.toString()),
              color: this.drivingLessonColor
            }
          ];
        });
      });
  }

  private getAllInternalExamsForStudent() {
    this.internalExamService.findAllActualByStudent(this.email)
      .subscribe(exams => {
        exams.forEach(exam => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromInternalExamForStudent(exam),
              start: parseISO(exam.startTime.toString()),
              end: parseISO(exam.endTime.toString()),
              color: this.internalExamColor
            }
          ];
        });
      });
  }

  /**
   * Metody własne do tworzenia danych wydarzenia
   * wyświetlanych w oknie szczegółów:
   */
  protected getEventTitleFromLecture(lecture: Lecture): string {
    const additionalInfo = lecture.additionalInfo == null ? "" :
      ("\n- ").concat(lecture.additionalInfo);
    return "WYKŁAD"
      .concat("\n- Temat: ").concat(lecture.subject)
      .concat(additionalInfo);
  }

  protected getEventTitleFromDrivingLessonForEmployee(lesson: DrivingLesson, student: Student): string {
    if (lesson.employee != null && lesson.lessonStatus != null) {
      return "JAZDA SZKOLENIOWA"
        .concat("\n- Kursant: ").concat(<string>student.fullName)
        .concat(" (").concat(<string>student.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(lesson.lessonStatus));
    }
    return "ERROR";
  }

  protected getEventTitleFromDrivingLessonForStudent(lesson: DrivingLesson): string {
    if (lesson.employee != null && lesson.lessonStatus != null) {
      return "JAZDA SZKOLENIOWA"
        .concat("\n- Instruktor: ").concat(<string>lesson.employee.fullName)
        .concat(" (").concat(<string>lesson.employee.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(lesson.lessonStatus));
    }
    return "ERROR";
  }

  protected getEventTitleFromInternalExamForEmployee(exam: InternalExam, student: Student): string {
    if (exam.examType != null && exam.employee != null && exam.lessonStatus != null) {
      return ExamType.fullTranslate(exam.examType)
        .concat("\n- Kursant: ").concat(<string>student.fullName)
        .concat(" (").concat(<string>student.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(exam.lessonStatus));
    }
    return "ERROR";
  }

  protected getEventTitleFromInternalExamForStudent(exam: InternalExam): string {
    if (exam.examType != null && exam.employee != null && exam.lessonStatus != null) {
      return ExamType.fullTranslate(exam.examType)
        .concat("\n- Prowadzący: ").concat(<string>exam.employee.fullName)
        .concat(" (").concat(<string>exam.employee.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(exam.lessonStatus));
    }
    return "ERROR";
  }

}
