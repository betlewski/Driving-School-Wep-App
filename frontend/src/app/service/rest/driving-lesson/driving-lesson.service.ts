import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {DrivingLesson} from "../../../model/driving-lesson.model";
import {DrivingLessonRest} from "../../../utils/driving-lesson-rest";
import {LessonStatus} from "../../../utils/lesson-status";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania jazdami szkoleniowymi.
 */
export class DrivingLessonService {

  private DRIVING_LESSON_URL = environment.restUrl + '/driving';
  private FIND_ALL_BY_STUDENT_URL = this.DRIVING_LESSON_URL + '/all/byStudent';
  private FIND_ALL_BY_EMPLOYEE_URL = this.DRIVING_LESSON_URL + '/all/byEmployee';
  private FIND_ALL_ACTUAL_BY_STUDENT_URL = this.DRIVING_LESSON_URL + '/all/actual/byStudent';
  private FIND_ALL_ACTUAL_BY_EMPLOYEE_URL = this.DRIVING_LESSON_URL + '/all/actual/byEmployee';
  private ADD_LESSON_URL = this.DRIVING_LESSON_URL + '/add';
  private EDIT_LESSON_STATUS_URL = this.DRIVING_LESSON_URL + '/edit/status';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByStudent(email: string): Observable<DrivingLesson[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<DrivingLesson[]>(this.FIND_ALL_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllByEmployee(email: string): Observable<DrivingLessonRest[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<DrivingLessonRest[]>(this.FIND_ALL_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public findAllActualByStudent(email: string): Observable<DrivingLesson[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<DrivingLesson[]>(this.FIND_ALL_ACTUAL_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllActualByEmployee(email: string): Observable<DrivingLessonRest[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<DrivingLessonRest[]>(this.FIND_ALL_ACTUAL_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public addLesson(studentEmail: string, employeeEmail: string, lesson: DrivingLesson): Observable<DrivingLesson> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams()
      .set("student", studentEmail)
      .set("employee", employeeEmail);
    return this.http.post<DrivingLesson>(this.ADD_LESSON_URL, lesson,
      {headers: headers, params: params});
  }

  public editLessonStatus(lessonId: number, lessonStatus: LessonStatus): Observable<DrivingLesson> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", lessonId.toString());
    return this.http.put<DrivingLesson>(this.EDIT_LESSON_STATUS_URL, lessonStatus,
      {headers: headers, params: params});
  }

  public acceptLessonRequest(lessonId: number): Observable<DrivingLesson> {
    return this.editLessonStatus(lessonId, LessonStatus.ACCEPTED);
  }

  public rejectLessonRequest(lessonId: number): Observable<DrivingLesson> {
    return this.editLessonStatus(lessonId, LessonStatus.REJECTED);
  }

  public passLesson(lessonId: number): Observable<DrivingLesson> {
    return this.editLessonStatus(lessonId, LessonStatus.PASSED);
  }

  public failLesson(lessonId: number): Observable<DrivingLesson> {
    return this.editLessonStatus(lessonId, LessonStatus.FAILED);
  }

}
