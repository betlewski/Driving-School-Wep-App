import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {InternalExam} from "../../../model/internal-exam.model";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania egzaminami wewnętrznymi.
 */
export class InternalExamService {

  private INTERNAL_EXAM_URL = environment.restUrl + '/exam';
  private FIND_ALL_BY_EMAIL_URL = this.INTERNAL_EXAM_URL + '/all/byEmail';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByEmail(email: string): Observable<InternalExam[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExam[]>(this.FIND_ALL_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

}
