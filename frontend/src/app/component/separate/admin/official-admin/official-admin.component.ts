import {Component, OnInit} from '@angular/core';
import {Official} from "../../../../model/official.model";
import {DocumentService} from "../../../../service/rest/document/document.service";
import {PaymentService} from "../../../../service/rest/payment/payment.service";
import {ProcessingStatus} from "../../../../utils/processing-status";
import {Student} from "../../../../model/student.model";
import {StudentService} from "../../../../service/rest/student/student.service";

@Component({
  selector: 'app-official-admin',
  templateUrl: './official-admin.component.html',
  styleUrls: ['./official-admin.component.css']
})
/**
 * Panel zarządzania zgłoszeniami
 * dotyczącymi dokumentów i płatności.
 */
export class OfficialAdminComponent implements OnInit {

  students: Student[] = [];
  chosenStudent: Student | null = null;

  officialsToSort = new Array<Official>();
  officialsToComplete = new Array<Official>();
  officialsRequested = new Array<Official>();
  officialsCompleted = new Array<Official>();

  constructor(private documentService: DocumentService,
              private paymentService: PaymentService,
              private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.findAllStudents();
  }

  private findAllStudents() {
    this.studentService.findAll().subscribe(
      students => {
        this.students = students;
        this.students = this.students.sort(
          // @ts-ignore
          (a, b) => a.fullName?.localeCompare(b.fullName));
      });
  }

  public changeStudent() {
    this.refreshData();
    // @ts-ignore
    if (this.chosenStudent != null && this.chosenStudent != "") {
      let email = this.chosenStudent.email;
      email = email != null ? email : "";
      this.getOfficials(email);
    } else {
      this.chosenStudent = null;
    }
  }

  private refreshData() {
    this.officialsToSort = new Array<Official>();
    this.officialsToComplete = new Array<Official>();
    this.officialsRequested = new Array<Official>();
    this.officialsCompleted = new Array<Official>();
  }

  private getOfficials(email: string) {
    this.getOfficialsFromDocuments(email);
  }

  private getOfficialsFromDocuments(email: string) {
    this.documentService.findAllByEmail(email)
      .subscribe(documents => {
        const officialsFromMap = Official.mapFromDocuments(documents);
        officialsFromMap.forEach(official =>
          this.officialsToSort.push(official));
        this.getOfficialsFromPayments(email);
      });
  }

  private getOfficialsFromPayments(email: string) {
    this.paymentService.findAllByEmail(email)
      .subscribe(payments => {
        const officialsFromMap = Official.mapFromPayments(payments);
        officialsFromMap.forEach(official =>
          this.officialsToSort.push(official));
        this.sortOfficials();
      });
  }

  private sortOfficials() {
    this.sortOfficialsToComplete();
    this.sortOfficialsRequested();
    this.sortOfficialsCompleted();
  }

  private sortOfficialsToComplete() {
    this.officialsCompleted = this.officialsToSort.filter(official =>
      ProcessingStatus.getFromTranslation(official.status) == ProcessingStatus.COMPLETED)
      .sort((a, b) => a.description.localeCompare(b.description));
  }

  private sortOfficialsRequested() {
    this.officialsRequested = this.officialsToSort.filter(official =>
      ProcessingStatus.getFromTranslation(official.status) == ProcessingStatus.REQUESTED)
      .sort((a, b) => a.description.localeCompare(b.description));
  }

  private sortOfficialsCompleted() {
    this.officialsToComplete = this.officialsToSort.filter(official =>
      ProcessingStatus.getFromTranslation(official.status) == ProcessingStatus.TO_COMPLETE)
      .sort((a, b) => a.description.localeCompare(b.description));
  }

  public accept(official: Official) {
    if (official.type == Official.DOCUMENT_TYPE) {
      this.documentService.accept(official.id)
        .subscribe(() => this.changeStudent());
    } else if (official.type == Official.PAYMENT_TYPE) {
      this.paymentService.accept(official.id)
        .subscribe(() => this.changeStudent());
    }
  }

  public reject(official: Official) {
    if (official.type == Official.DOCUMENT_TYPE) {
      this.documentService.reject(official.id)
        .subscribe(() => this.changeStudent());
    } else if (official.type == Official.PAYMENT_TYPE) {
      this.paymentService.reject(official.id)
        .subscribe(() => this.changeStudent());
    }
  }

}
