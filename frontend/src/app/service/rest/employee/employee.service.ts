import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {Employee} from "../../../model/employee.model";
import {EmployeeRole} from "../../../utils/employee-role";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzaniamia pracownikami.
 */
export class EmployeeService {

  private EMPLOYEE_URL = environment.restUrl + '/employee';
  private FIND_ALL_NOT_ADMINS_URL = this.EMPLOYEE_URL + '/all/notAdmins';
  private FIND_ALL_BY_ROLE_URL = this.EMPLOYEE_URL + '/all/byRole';
  private FIND_BY_EMAIL_URL = this.EMPLOYEE_URL + '/byEmail';
  private ADD_URL = this.EMPLOYEE_URL + '/add';
  private EDIT_URL = this.EMPLOYEE_URL + '/edit';
  private EDIT_FULL_URL = this.EMPLOYEE_URL + '/edit/full';
  private CHANGE_PASSWORD_URL = this.EMPLOYEE_URL + '/edit/password';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllNotAdmins(): Observable<Employee[]> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<Employee[]>(this.FIND_ALL_NOT_ADMINS_URL,
      {headers: headers});
  }

  public findAllByRole(role: EmployeeRole): Observable<Employee[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("role", EmployeeRole[role]);
    return this.http.get<Employee[]>(this.FIND_ALL_BY_ROLE_URL,
      {headers: headers, params: params});
  }

  public findByEmail(email: string): Observable<Employee> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Employee>(this.FIND_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public add(employee: Employee): Observable<Employee> {
    const headers = this.authService.getAuthHeaders();
    return this.http.post<Employee>(this.ADD_URL, employee,
      {headers: headers});
  }

  public edit(email: string, newEmployee: Employee): Observable<Employee> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.put<Employee>(this.EDIT_URL, newEmployee,
      {headers: headers, params: params});
  }

  public editFull(email: string, newEmployee: Employee): Observable<Employee> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.put<Employee>(this.EDIT_FULL_URL, newEmployee,
      {headers: headers, params: params});
  }

  public changePassword(oldPassword: string, newPassword: string): Observable<Employee> {
    const email = this.authService.getUserEmail();
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams()
      .set("email", email)
      .set("old", oldPassword);
    return this.http.put<Employee>(this.CHANGE_PASSWORD_URL, newPassword,
      {headers: headers, params: params});
  }

}
