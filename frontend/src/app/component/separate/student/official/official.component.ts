import {Component, OnInit} from '@angular/core';
import {Official} from "../../../../model/official.model";
import {AuthService} from "../../../../service/auth/auth.service";
import {DocumentService} from "../../../../service/rest/document/document.service";
import {PaymentService} from "../../../../service/rest/payment/payment.service";
import {ProcessingStatus} from "../../../../utils/processing-status";

@Component({
  selector: 'app-official',
  templateUrl: './official.component.html',
  styleUrls: ['./official.component.css']
})
/**
 * Panel dokumentów i płatności kursanta
 */
export class OfficialComponent implements OnInit {

  officialsToSort = new Array<Official>();
  officialsToComplete = new Array<Official>();
  officialsRequested = new Array<Official>();
  officialsCompleted = new Array<Official>();

  constructor(private authService: AuthService,
              private documentService: DocumentService,
              private paymentService: PaymentService) {
  }

  ngOnInit(): void {
    const email = this.authService.getUserEmail();
    this.getOfficials(email);
  }

  public request(official: Official) {
    if (official.type == Official.DOCUMENT_TYPE) {
      this.documentService.request(official.id)
        .subscribe(() => this.refreshData());
    } else if (official.type == Official.PAYMENT_TYPE) {
      this.paymentService.request(official.id)
        .subscribe(() => this.refreshData());
    }
  }

  private refreshData() {
    this.officialsToSort = new Array<Official>();
    this.officialsToComplete = new Array<Official>();
    this.officialsRequested = new Array<Official>();
    this.officialsCompleted = new Array<Official>();
    this.ngOnInit();
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

}
