import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {Student} from "../../../model/student.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthService} from "../../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania kursantami
 */
export class StudentService {

  private STUDENT_URL = environment.restUrl + '/student';
  private REGISTER_URL = this.STUDENT_URL + '/add';
  private FIND_BY_EMAIL_URL = this.STUDENT_URL + '/byEmail';
  private EDIT_URL = this.STUDENT_URL + '/edit';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public register(student: Student) {
    return this.http.post<Student>(this.REGISTER_URL, student);
  }

  public findByEmail(email: string): Observable<Student> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Student>(this.FIND_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public edit(email: string, newStudent: Student): Observable<Student> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.put<Student>(this.EDIT_URL, newStudent,
      {headers: headers, params: params});
  }

}
