import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {InternalExam} from "../../../model/internal-exam.model";
import {InternalExamRest} from "../../../utils/internal-exam-rest";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania egzaminami wewnętrznymi.
 */
export class InternalExamService {

  private INTERNAL_EXAM_URL = environment.restUrl + '/exam';
  private FIND_ALL_BY_STUDENT_URL = this.INTERNAL_EXAM_URL + '/all/byEmail';
  private FIND_ALL_ONGOING_BY_EMPLOYEE_URL = this.INTERNAL_EXAM_URL + '/all/ongoing/byEmployee';
  private ADD_EXAM_URL = this.INTERNAL_EXAM_URL + '/add';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByEmail(email: string): Observable<InternalExam[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExam[]>(this.FIND_ALL_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllByEmployee(email: string): Observable<InternalExamRest[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExamRest[]>(this.FIND_ALL_ONGOING_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public addExam(studentEmail: string, employeeEmail: string, exam: InternalExam): Observable<InternalExam> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams()
      .set("student", studentEmail)
      .set("employee", employeeEmail);
    return this.http.post<InternalExam>(this.ADD_EXAM_URL, exam,
      {headers: headers, params: params});
  }

}
