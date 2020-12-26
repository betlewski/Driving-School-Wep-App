import {Component, OnInit} from '@angular/core';
import {Utils} from "../../../../utils/utils";
import {AuthService} from "../../../../service/auth/auth.service";
import {LessonStatus} from "../../../../utils/lesson-status";
import {InternalExamRest} from "../../../../utils/internal-exam-rest";
import {InternalExamService} from "../../../../service/rest/internal-exam/internal-exam.service";
import {InternalExam} from "../../../../model/internal-exam.model";

@Component({
  selector: 'app-exam-employee',
  templateUrl: './exam-employee.component.html',
  styleUrls: ['./exam-employee.component.css']
})
/**
 * Panel pracownika do zarządzania egzaminami wewnętrznymi.
 */
export class ExamEmployeeComponent implements OnInit {

  requestedExams: InternalExamRest[] = [];
  activeExams: InternalExamRest[] = [];
  chosenExam: InternalExam | null = null;

  examStatus: string = "";
  startTime: Date | null = null;
  endTime: Date | null = null;
  result: number | null = null;
  isPassed: boolean | null = null;

  resultsView: boolean = false;
  viewMode: boolean = true;
  utils = new Utils();

  constructor(private authService: AuthService,
              private internalExamService: InternalExamService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getAllInternalExams(email);
  }

  private getAllInternalExams(email: string) {
    this.internalExamService.findAllByEmployee(email)
      .subscribe(exams => this.sortExamsByStatus(exams));
  }

  private sortExamsByStatus(exams: InternalExamRest[]): void {
    exams.forEach(value => {
      const examStatus = value.internalExam.lessonStatus;
      if (examStatus != null) {
        switch (LessonStatus[examStatus] as unknown) {
          case LessonStatus.REQUESTED:
            this.requestedExams.push(value);
            break;
          case LessonStatus.ACCEPTED:
            this.activeExams.push(value);
            break;
          default:
            break;
        }
      }
    });
    this.sortExamsById(this.requestedExams);
    this.sortExamsById(this.activeExams);
  }

  private sortExamsById(lessons: InternalExamRest[]): void {
    lessons.sort((a, b) =>
      // @ts-ignore
      a.internalExam.id - b.internalExam.id);
  }

  public changeExamStatus() {
    this.resultsView = this.examStatus == LessonStatus[LessonStatus.PASSED];
    this.cleanResultsData();
  }

  public changeExam() {
    // @ts-ignore
    if (this.chosenExam != null && this.chosenExam != "") {
      this.startTime = this.chosenExam.startTime;
      this.endTime = this.chosenExam.endTime;
    } else {
      this.cleanFormData();
    }
  }

  public editData() {
    // @ts-ignore
    if (this.chosenExam != null && this.chosenExam != "") {
      this.viewMode = false;
    }
  }

  public saveData() {
    if (this.chosenExam != null && this.chosenExam.id != null && this.checkIfDataNotEmpty()) {
      const exam = new InternalExam(null, null, null,
        // @ts-ignore
        null, null, this.examStatus, this.result, this.isPassed);
      this.internalExamService.editExam(this.chosenExam.id, exam)
        .subscribe(() => {
          this.refreshData();
          this.viewMode = true;
          this.resultsView = false;
        });
    } else {
      this.cleanResultsData();
    }
  }

  private checkIfDataNotEmpty(): boolean {
    if (this.examStatus == LessonStatus[LessonStatus.PASSED]) {
      return this.result != null && this.result >= 0 && this.result <= 100
        && this.isPassed !== null;
    } else return this.examStatus == LessonStatus[LessonStatus.FAILED];
  }

  public acceptRequest(exam: InternalExam): void {
    if (exam.id != null) {
      this.internalExamService.acceptExamRequest(exam.id)
        .subscribe(() => this.refreshData());
    }
  }

  public rejectRequest(exam: InternalExam): void {
    if (exam.id != null) {
      this.internalExamService.rejectExamRequest(exam.id)
        .subscribe(() => this.refreshData());
    }
  }

  private refreshData(): void {
    this.cleanData();
    this.ngOnInit();
  }

  private cleanData(): void {
    this.chosenExam = null;
    this.requestedExams = [];
    this.activeExams = [];
    this.cleanFormData();
  }

  private cleanFormData(): void {
    this.examStatus = "";
    this.startTime = null;
    this.endTime = null;
    this.cleanResultsData();
  }

  private cleanResultsData(): void {
    this.result = null;
    this.isPassed = null;
  }

}
