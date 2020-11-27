import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {Student} from "../../../model/student.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania kursantami
 */
export class StudentService {

  private STUDENT_URL = environment.restUrl + '/student';
  private REGISTER_URL = this.STUDENT_URL + '/add';

  constructor(private http: HttpClient) {
  }

  public register(student: Student) {
    return this.http.post<Student>(this.REGISTER_URL, student);
  }

}
