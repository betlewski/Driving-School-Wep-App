import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {Lecture} from "../../../model/lecture.model";
import {TheoryLessons} from "../../../model/theory-lessons.model";
import {LessonStatus} from "../../../utils/lesson-status";
import {TheoryLessonsRest} from "../../../utils/theory-lessons-rest";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania zajęciami teoretycznymi.
 */
export class TheoryLessonsService {

  private THEORY_LESSONS_URL = environment.restUrl + '/theory';
  private FIND_ACTUAL_LECTURES_BY_STUDENT_URL = this.THEORY_LESSONS_URL + '/lectures/actual/byStudent';
  private IS_ACTIVE_BY_STUDENT_URL = this.THEORY_LESSONS_URL + '/isActive/byStudent';
  private FIND_ALL_BY_STUDENT_AND_STATUS_URL = this.THEORY_LESSONS_URL + '/all/byStudent/byStatus';
  private FIND_ALL_BY_EMPLOYEE_URL = this.THEORY_LESSONS_URL + '/all/byEmployee';
  private FIND_ACTIVE_BY_STUDENT_URL = this.THEORY_LESSONS_URL + '/active/byStudent';
  private ADD_URL = this.THEORY_LESSONS_URL + '/add';
  private EDIT_LESSON_STATUS_URL = this.THEORY_LESSONS_URL + '/edit/status';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findActualLecturesByStudent(email: string): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Lecture[]>(this.FIND_ACTUAL_LECTURES_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public isActiveByStudent(email: string): Observable<boolean> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<boolean>(this.IS_ACTIVE_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllByStudentAndStatus(email: string, status: LessonStatus): Observable<TheoryLessons[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams()
      .set("email", email)
      .set("status", LessonStatus[status]);
    return this.http.get<TheoryLessons[]>(this.FIND_ALL_BY_STUDENT_AND_STATUS_URL,
      {headers: headers, params: params});
  }

  public findAllByEmployee(email: string): Observable<TheoryLessonsRest[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<TheoryLessonsRest[]>(this.FIND_ALL_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public findActiveByStudent(email: string): Observable<TheoryLessons> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<TheoryLessons>(this.FIND_ACTIVE_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public add(email: string, seriesId: number): Observable<TheoryLessons> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.post<TheoryLessons>(this.ADD_URL, seriesId,
      {headers: headers, params: params});
  }

  public editLessonStatus(lessonId: number, lessonStatus: LessonStatus): Observable<TheoryLessons> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", lessonId.toString());
    return this.http.put<TheoryLessons>(this.EDIT_LESSON_STATUS_URL, lessonStatus,
      {headers: headers, params: params});
  }

  public acceptLessonRequest(lessonId: number): Observable<TheoryLessons> {
    return this.editLessonStatus(lessonId, LessonStatus.ACCEPTED);
  }

  public rejectLessonRequest(lessonId: number): Observable<TheoryLessons> {
    return this.editLessonStatus(lessonId, LessonStatus.REJECTED);
  }

  public passLesson(lessonId: number): Observable<TheoryLessons> {
    return this.editLessonStatus(lessonId, LessonStatus.PASSED);
  }

  public failLesson(lessonId: number): Observable<TheoryLessons> {
    return this.editLessonStatus(lessonId, LessonStatus.FAILED);
  }

}
