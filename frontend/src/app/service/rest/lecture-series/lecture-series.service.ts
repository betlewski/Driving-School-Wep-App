import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {LectureSeries} from "../../../model/lecture-series.model";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania cyklami wykładów.
 */
export class LectureSeriesService {

  private LECTURE_SERIES_URL = environment.restUrl + '/series';
  private FIND_ALL_FREE_SERIES_URL = this.LECTURE_SERIES_URL + '/all/free';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllFreeSeries(): Observable<LectureSeries[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<LectureSeries[]>(this.FIND_ALL_FREE_SERIES_URL,
      {headers: headers});
  }

}
