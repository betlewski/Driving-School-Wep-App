import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {Lecture} from "../../../model/lecture.model";
import {TheoryLessons} from "../../../model/theory-lessons.model";
import {LessonStatus} from "../../../utils/lesson-status";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania zajęciami teoretycznymi.
 */
export class TheoryLessonsService {

  private THEORY_LESSONS_URL = environment.restUrl + '/theory';
  private FIND_ACTUAL_LECTURES_BY_STUDENT_URL = this.THEORY_LESSONS_URL + '/lectures/actual/byStudent';
  private IS_ACTIVE_BY_EMAIL_URL = this.THEORY_LESSONS_URL + '/isActive/byEmail';
  private ADD_URL = this.THEORY_LESSONS_URL + '/add';
  private FIND_ALL_BY_EMAIL_AND_STATUS_URL = this.THEORY_LESSONS_URL + '/all/byEmail/byStatus';
  private FIND_ACTIVE_BY_EMAIL_URL = this.THEORY_LESSONS_URL + '/active/byEmail';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findActualLecturesByStudent(email: string): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Lecture[]>(this.FIND_ACTUAL_LECTURES_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public isActiveByEmail(email: string): Observable<boolean> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<boolean>(this.IS_ACTIVE_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public add(email: string, seriesId: number): Observable<TheoryLessons> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.post<TheoryLessons>(this.ADD_URL, seriesId,
      {headers: headers, params: params});
  }

  public findAllByEmailAndStatus(email: string, status: LessonStatus): Observable<TheoryLessons[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams()
      .set("email", email)
      .set("status", LessonStatus[status]);
    return this.http.get<TheoryLessons[]>(this.FIND_ALL_BY_EMAIL_AND_STATUS_URL,
      {headers: headers, params: params});
  }

  public findActiveByEmail(email: string): Observable<TheoryLessons> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<TheoryLessons>(this.FIND_ACTIVE_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

}
