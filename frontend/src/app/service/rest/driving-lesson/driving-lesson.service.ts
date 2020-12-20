import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {DrivingLesson} from "../../../model/driving-lesson.model";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania jazdami szkoleniowymi.
 */
export class DrivingLessonService {

  private DRIVING_LESSON_URL = environment.restUrl + '/driving';
  private FIND_ALL_BY_STUDENT_URL = this.DRIVING_LESSON_URL + '/all/byEmail';
  private FIND_ALL_BY_EMPLOYEE_URL = this.DRIVING_LESSON_URL + '/all/byEmployee';
  private ADD_LESSON_URL = this.DRIVING_LESSON_URL + '/add';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByEmail(email: string): Observable<DrivingLesson[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<DrivingLesson[]>(this.FIND_ALL_BY_STUDENT_URL,
      {headers: headers, params: params});
  }

  public findAllByEmployee(email: string): Observable<DrivingLesson[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<DrivingLesson[]>(this.FIND_ALL_BY_EMPLOYEE_URL,
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

}
