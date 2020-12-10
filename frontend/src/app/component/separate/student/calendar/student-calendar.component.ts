import {Component, OnInit} from '@angular/core';
import {CalendarComponent, colors} from "../../../common/calendar/calendar.component";
import {parseISO} from "date-fns";
import {Lecture} from "../../../../model/lecture.model";
import {DrivingLesson} from "../../../../model/driving-lesson.model";
import {InternalExam} from "../../../../model/internal-exam.model";
import {LessonStatus} from "../../../../utils/lesson-status";
import {ExamType} from "../../../../utils/exam-type";

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

  private lectureColor = colors.yellow;
  private drivingLessonColor = colors.green;
  private internalExamColor = colors.red;

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllEvents(email);
  }

  private getAllEvents(email: string) {
    this.getAllLectures(email);
    this.getAllDrivingLessons(email);
    this.getAllInternalExams(email);
  }

  private getAllLectures(email: string) {
    this.theoryLessonsService.findAllLecturesByEmail(email)
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

  private getAllDrivingLessons(email: string) {
    this.drivingLessonService.findAllByEmail(email)
      .subscribe(lessons => {
        lessons.forEach(lesson => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromDrivingLesson(lesson),
              start: parseISO(lesson.startTime.toString()),
              end: parseISO(lesson.endTime.toString()),
              color: this.drivingLessonColor
            }
          ];
        });
      });
  }

  private getAllInternalExams(email: string) {
    this.internalExamService.findAllByEmail(email)
      .subscribe(exams => {
        exams.forEach(exam => {
          this.events = [
            ...this.events,
            {
              title: this.getEventTitleFromInternalExam(exam),
              start: parseISO(exam.startTime.toString()),
              end: parseISO(exam.endTime.toString()),
              color: this.internalExamColor
            }
          ];
        });
      });
  }

  private getEventTitleFromLecture(lecture: Lecture): string {
    return "WYKŁAD"
      .concat("\n- Temat: ").concat(lecture.subject)
      .concat("\n- ").concat(lecture.additionalInfo);
  }

  private getEventTitleFromDrivingLesson(lesson: DrivingLesson): string {
    if (lesson.employee != null && lesson.lessonStatus != null) {
      return "JAZDA SZKOLENIOWA"
        .concat("\n- Instruktor: ").concat(lesson.employee.fullName)
        .concat(" (").concat(lesson.employee.email).concat(")")
        .concat("\n- Status: ").concat(LessonStatus.translate(lesson.lessonStatus));
    }
    return "";
  }

  private getEventTitleFromInternalExam(exam: InternalExam): string {
    return ExamType.fullTranslate(exam.examType)
      .concat("\n- Prowadzący: ").concat(exam.employee.fullName)
      .concat(" (").concat(exam.employee.email).concat(")")
      .concat("\n- Status: ").concat(LessonStatus.translate(exam.lessonStatus));
  }

}
