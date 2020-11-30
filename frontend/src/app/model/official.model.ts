import {Payment} from "./payment.model";
import {PaymentType} from "../utils/payment-type";
import {ProcessingStatus} from "../utils/processing-status";
import {Document} from "./document.model";
import {DocumentType} from "../utils/document-type";
import {Utils} from "../utils/utils";

/**
 * Sprawa urzędowa wyświetlana w tabeli (dokument, płatność)
 */
export class Official {

  id: number;
  description: string;
  status: string;
  type: string;
  submissionTime: string;

  constructor(id: number, description: string, status: string, type: string, submissionTime: string) {
    this.id = id;
    this.description = description;
    this.status = status;
    this.type = type;
    this.submissionTime = submissionTime;
  }

  public static PAYMENT_TYPE = "PŁATNOŚĆ";
  public static DOCUMENT_TYPE = "DOKUMENT";

  public static mapFromPayment(payment: Payment): Official {
    const description = PaymentType.translate(payment.paymentType)
      .concat(" (").concat(payment.id.toString()).concat(") - ")
      .concat(payment.price.toString()).concat(" PLN");
    const status = ProcessingStatus.translate(payment.processingStatus);
    const submissionTime = Utils.checkStringIfNotEmpty(payment.paymentTime) ? payment.paymentTime : "-";
    return new Official(payment.id, description, status, this.PAYMENT_TYPE, submissionTime);
  }

  public static mapFromPayments(payments: Payment[]): Official[] {
    let officials = Array<Official>();
    payments.forEach(payment =>
      officials.push(this.mapFromPayment(payment))
    );
    return officials;
  }

  public static mapFromDocument(document: Document): Official {
    const description = DocumentType.translate(document.documentType);
    const status = ProcessingStatus.translate(document.processingStatus);
    const submissionTime = Utils.checkStringIfNotEmpty(document.submissionTime) ? document.submissionTime : "-";
    return new Official(document.id, description, status, this.DOCUMENT_TYPE, submissionTime);
  }

  public static mapFromDocuments(document: Document[]): Official[] {
    let officials = Array<Official>();
    document.forEach(document =>
      officials.push(this.mapFromDocument(document))
    );
    return officials;
  }

}

