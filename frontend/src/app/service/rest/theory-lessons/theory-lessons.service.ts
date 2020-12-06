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
 * Serwis do zarządzania zajęciami teoretycznymi.
 */
export class TheoryLessonsService {

  private THEORY_LESSONS_URL = environment.restUrl + '/theory';
  private FIND_ALL_LECTURES_BY_EMAIL_URL = this.THEORY_LESSONS_URL + '/lectures/all/byEmail';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllLecturesByEmail(email: string): Observable<Lecture[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Lecture[]>(this.FIND_ALL_LECTURES_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

}
