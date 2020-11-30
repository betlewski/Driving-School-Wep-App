import {Payment} from "./payment.model";
import {PaymentType} from "../utils/payment-type";
import {ProcessingStatus} from "../utils/processing-status";
import {Document} from "./document.model";
import {DocumentType} from "../utils/document-type";

/**
 * Sprawa urzędowa wyświetlana w tabeli (dokument, płatność)
 */
export class Official {

  id: number;
  description: string;
  status: ProcessingStatus;
  submissionTime: string;

  constructor(id: number, description: string, status: ProcessingStatus, submissionTime: string) {
    this.id = id;
    this.description = description;
    this.status = status;
    this.submissionTime = submissionTime;
  }

  public static mapFromPayment(payment: Payment): Official {
    const description = PaymentType.translate(payment.type)
      .concat(" - ").concat(payment.price.toString());
    return new Official(payment.id, description, payment.status, payment.paymentTime);
  }

  public static mapFromPayments(payments: Payment[]): Official[] {
    let officials = Array<Official>();
    payments.forEach(payment =>
      officials.push(this.mapFromPayment(payment))
    );
    return officials;
  }

  public static mapFromDocument(document: Document): Official {
    return new Official(document.id, DocumentType.translate(document.type),
      document.status, document.submissionTime);
  }

  public static mapFromDocuments(document: Document[]): Official[] {
    let officials = Array<Official>();
    document.forEach(document =>
      officials.push(this.mapFromDocument(document))
    );
    return officials;
  }

}

