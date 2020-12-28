import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {LectureSeries} from "../../../model/lecture-series.model";
import {Lecture} from "../../../model/lecture.model";
import {LectureSeriesStatus} from "../../../utils/lecture-series-status";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania cyklami wykładów.
 */
export class LectureSeriesService {

  private LECTURE_SERIES_URL = environment.restUrl + '/series';
  private FIND_ALL_FREE_SERIES_URL = this.LECTURE_SERIES_URL + '/all/free';
  private FIND_ALL_SERIES_BY_EMPLOYEE_URL = this.LECTURE_SERIES_URL + '/all/byEmployee';
  private FIND_ALL_LECTURES_BY_EMPLOYEE_URL = this.LECTURE_SERIES_URL + '/lectures/all/byEmployee';
  private ADD_SERIES_URL = this.LECTURE_SERIES_URL + '/add';
  private EDIT_SERIES_STATUS_URL = this.LECTURE_SERIES_URL + '/edit/status';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllFreeSeries(): Observable<LectureSeries[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<LectureSeries[]>(this.FIND_ALL_FREE_SERIES_URL,
      {headers: headers});
  }

  public findAllSeriesByEmployee(email: string): Observable<LectureSeries[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<LectureSeries[]>(this.FIND_ALL_SERIES_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public findAllLecturesByEmployee(email: string): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Lecture[]>(this.FIND_ALL_LECTURES_BY_EMPLOYEE_URL,
      {headers: headers, params: params});
  }

  public addSeries(email: string, lectureIds: number[]): Observable<LectureSeries> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.post<LectureSeries>(this.ADD_SERIES_URL, lectureIds,
      {headers: headers, params: params});
  }

  public editSeriesStatus(id: number, status: LectureSeriesStatus): Observable<LectureSeries> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", id.toString());
    return this.http.put<LectureSeries>(this.EDIT_SERIES_STATUS_URL, JSON.stringify(status),
      {headers: headers, params: params});
  }

}
