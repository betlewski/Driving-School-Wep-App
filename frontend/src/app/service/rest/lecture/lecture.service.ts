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

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllLecturesBySeriesId(id: number): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("id", id.toString());
    return this.http.get<Lecture[]>(this.FIND_ALL_LECTURES_BY_SERIES_ID_URL,
      {headers: headers, params: params});
  }

}
