import {ProcessingStatus} from "../utils/processing-status";
import {PaymentType} from "../utils/payment-type";

/**
 * Płatność w ramach kursu.
 */
export class Payment {

  id: number;
  type: PaymentType;
  price: number;
  status: ProcessingStatus;
  paymentTime: string;

  constructor(id: number, type: PaymentType, price: number, status: ProcessingStatus, paymentTime: string) {
    this.id = id;
    this.type = type;
    this.price = price;
    this.status = status;
    this.paymentTime = paymentTime;
  }

}
