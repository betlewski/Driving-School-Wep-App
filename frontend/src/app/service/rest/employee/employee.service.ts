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
  private FIND_ALL_BY_ROLE = this.EMPLOYEE_URL + '/all/byRole';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByRole(role: EmployeeRole): Observable<Employee[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("role", EmployeeRole[role]);
    return this.http.get<Employee[]>(this.FIND_ALL_BY_ROLE,
      {headers: headers, params: params});
  }

}
