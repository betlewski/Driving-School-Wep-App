import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {Payment} from "../../../model/payment.model";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania płatnościami.
 */
export class PaymentService {

  private PAYMENT_URL = environment.restUrl + '/payment';
  private FIND_ALL_BY_EMAIL_URL = this.PAYMENT_URL + '/all/byEmail';
  private REQUEST_BY_ID_URL = this.PAYMENT_URL + '/edit/request';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByEmail(email: string): Observable<Payment[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Payment[]>(this.FIND_ALL_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public request(paymentId: number): Observable<Payment> {
    const headers = this.authService.getAuthHeaders();
    return this.http.put<Payment>(this.REQUEST_BY_ID_URL, paymentId,
      {headers: headers});
  }

}
