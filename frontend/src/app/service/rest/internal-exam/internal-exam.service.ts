import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {InternalExam} from "../../../model/internal-exam.model";
import {InternalExamRest} from "../../../utils/internal-exam-rest";
import {LessonStatus} from "../../../utils/lesson-status";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania egzaminami wewnętrznymi.
 */
export class InternalExamService {

  private INTERNAL_EXAM_URL = environment.restUrl + '/exam';
  private FIND_ALL_BY_STUDENT_URL = this.INTERNAL_EXAM_URL + '/all/byStudent';
  private FIND_ALL_BY_EMPLOYEE_URL = this.INTERNAL_EXAM_URL + '/all/byEmployee';
  private FIND_ALL_ACTUAL_BY_STUDENT_URL = this.INTERNAL_EXAM_URL + '/all/actual/byStudent';
  private FIND_ALL_ACTUAL_BY_EMPLOYEE_URL = this.INTERNAL_EXAM_URL + '/all/actual/byEmployee';
  private ADD_EXAM_URL = this.INTERNAL_EXAM_URL + '/add';
  private EDIT_EXAM_URL = this.INTERNAL_EXAM_URL + '/edit';
  private EDIT_EXAM_STATUS_URL = this.INTERNAL_EXAM_URL + '/edit/status';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByStudent(email: string): Observable<InternalExam[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExam[]>(this.FIND_ALL_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllByEmployee(email: string): Observable<InternalExamRest[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExamRest[]>(this.FIND_ALL_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public findAllActualByStudent(email: string): Observable<InternalExam[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExam[]>(this.FIND_ALL_ACTUAL_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllActualByEmployee(email: string): Observable<InternalExamRest[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<InternalExamRest[]>(this.FIND_ALL_ACTUAL_BY_EMPLOYEE_URL,
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

  public editExam(examId: number, exam: InternalExam): Observable<InternalExam> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", examId.toString());
    return this.http.put<InternalExam>(this.EDIT_EXAM_URL, exam,
      {headers: headers, params: params});
  }

  public editExamStatus(examId: number, examStatus: LessonStatus): Observable<InternalExam> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", examId.toString());
    return this.http.put<InternalExam>(this.EDIT_EXAM_STATUS_URL, examStatus,
      {headers: headers, params: params});
  }

  public acceptExamRequest(examId: number): Observable<InternalExam> {
    return this.editExamStatus(examId, LessonStatus.ACCEPTED);
  }

  public rejectExamRequest(examId: number): Observable<InternalExam> {
    return this.editExamStatus(examId, LessonStatus.REJECTED);
  }

}
