import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "../../auth/auth.service";
import {Observable} from "rxjs";
import {Document} from "../../../model/document.model";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do zarządzania dokumentami.
 */
export class DocumentService {

  private DOCUMENT_URL = environment.restUrl + '/document';
  private FIND_ALL_BY_EMAIL_URL = this.DOCUMENT_URL + '/all/byEmail';
  private REQUEST_BY_ID_URL = this.DOCUMENT_URL + '/edit/request';

  constructor(private http: HttpClient,
              private authService: AuthService) {
  }

  public findAllByEmail(email: string): Observable<Document[]> {
    const headers = this.authService.getAuthHeaders();
    const params = new HttpParams().set("email", email);
    return this.http.get<Document[]>(this.FIND_ALL_BY_EMAIL_URL,
      {headers: headers, params: params});
  }

  public request(documentId: number): Observable<Document> {
    const headers = this.authService.getAuthHeaders();
    return this.http.put<Document>(this.REQUEST_BY_ID_URL, documentId,
      {headers: headers});
  }

}
