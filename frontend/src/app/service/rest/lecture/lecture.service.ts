import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {Lecture} from "../../../model/lecture.model";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania wykładami.
 */
export class LectureService {

  private LECTURE_URL = environment.restUrl + '/lecture';
  private FIND_ALL_LECTURES_BY_SERIES_ID_URL = this.LECTURE_URL + '/all/bySeriesId';
  private FIND_ALL_FREE_LECTURES_URL = this.LECTURE_URL + '/all/free';
  private CHECK_IF_SUM_HOURS_CORRECT_URL = this.LECTURE_URL + '/hours/isEnough/byLecturesSet';
  private ADD_LECTURE_URL = this.LECTURE_URL + '/add';
  private DELETE_LECTURE_URL = this.LECTURE_URL + '/delete';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllLecturesBySeriesId(id: number): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", id.toString());
    return this.http.get<Lecture[]>(this.FIND_ALL_LECTURES_BY_SERIES_ID_URL,
      {headers: headers, params: params});
  }

  public findAllFreeLectures(): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Lecture[]>(this.FIND_ALL_FREE_LECTURES_URL,
      {headers: headers});
  }

  public checkIfSumHoursCorrect(lectureSet: Lecture[]): Observable<Boolean> {
    const headers = this.authService.getAuthHeaders();
    return this.http.post<Boolean>(this.CHECK_IF_SUM_HOURS_CORRECT_URL, lectureSet,
      {headers: headers});
  }

  public addLecture(lecture: Lecture): Observable<Lecture> {
    const headers = this.authService.getAuthHeaders();
    return this.http.post<Lecture>(this.ADD_LECTURE_URL, lecture,
      {headers: headers});
  }

  public deleteLecture(lectureId: number): Observable<Boolean> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", lectureId.toString());
    return this.http.delete<Boolean>(this.DELETE_LECTURE_URL,
      {headers: headers, params: params});
  }

}
