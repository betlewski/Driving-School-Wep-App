import {ProcessingStatus} from "../utils/processing-status";
import {PaymentType} from "../utils/payment-type";

/**
 * Płatność w ramach kursu.
 */
export class Payment {

  id: number;
  paymentType: PaymentType;
  price: number;
  processingStatus: ProcessingStatus;
  paymentTime: string;

  constructor(id: number, paymentType: PaymentType, price: number, processingStatus: ProcessingStatus, paymentTime: string) {
    this.id = id;
    this.paymentType = paymentType;
    this.price = price;
    this.processingStatus = processingStatus;
    this.paymentTime = paymentTime;
  }

}
