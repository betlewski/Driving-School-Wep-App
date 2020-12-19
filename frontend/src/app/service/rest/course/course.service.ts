import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Course} from "../../../model/course.model";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {CourseReport} from "../../../model/course-report.model";
import {LicenseCategory} from "../../../utils/license-category";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania kursami
 */
export class CourseService {

  private COURSE_URL = environment.restUrl + '/course';
  private FIND_ACTIVE_COURSE_BY_EMAIL_URL = this.COURSE_URL + '/byEmail';
  private ADD_COURSE_URL = this.COURSE_URL + '/add';
  private GET_REPORT_BY_EMAIL_URL = this.COURSE_URL + '/report/byEmail';
  private GET_ALL_REPORTS_URL = this.COURSE_URL + '/report/all';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findActiveCourseByEmail(email: string): Observable<Course> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Course>(this.FIND_ACTIVE_COURSE_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public addCourse(email: string, licenseCategory: LicenseCategory): Observable<Course> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.post<Course>(this.ADD_COURSE_URL, JSON.stringify(licenseCategory.name.toUpperCase()),
      {headers: headers, params: params});
  }

  public getReportByEmail(email: string): Observable<CourseReport> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<CourseReport>(this.GET_REPORT_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public getAllReports(): Observable<Map<String, CourseReport>> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Map<String, CourseReport>>(this.GET_ALL_REPORTS_URL,
      {headers: headers});
  }

}
