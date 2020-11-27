import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JwtRequest} from "../../model/jwt-request.model";
import {JwtResponse} from "../../model/jwt-response.model";
import {CryptoJsService} from "../crypto-js/crypto-js.service";
import {map} from "rxjs/operators";
import {UserRole} from "../../utils/user-role";

@Injectable({
  providedIn: 'root'
})
/**
 * Serwis do uwierzytelniania użytkowników
 */
export class AuthService {

  private JWT_TOKEN = "jwt";
  private USER_EMAIL = "user-email";
  private USER_ROLE = "user-role";

  private AUTH_URL = environment.restUrl + "/authenticate";

  constructor(private http: HttpClient,
              private cryptoJsService: CryptoJsService) {
  }

  public authenticate(email: string, password: string) {
    const jwtRequest = new JwtRequest(email, password);
    return this.http.post<JwtResponse>(this.AUTH_URL, jwtRequest)
      .pipe(map(jwtResponse => {
        this.setItemsFromResponse(jwtResponse);
      }));
  }

  private setItemsFromResponse(jwtResponse: JwtResponse) {
    const jwtToken = jwtResponse.jwtToken;
    const userEmail = this.cryptoJsService.encrypt(jwtResponse.username);
    const userRole = this.cryptoJsService.encrypt(jwtResponse.userRole);
    this.setItems(jwtToken, userEmail, userRole);
  }

  public setItems(jwtToken: string, userEmail: string, userRole: string) {
    sessionStorage.setItem(this.JWT_TOKEN, jwtToken);
    sessionStorage.setItem(this.USER_EMAIL, userEmail);
    sessionStorage.setItem(this.USER_ROLE, userRole);
  }

  public clearItems() {
    sessionStorage.clear();
  }

  public getUserEmail(): string {
    const userEmail = sessionStorage.getItem(this.USER_EMAIL);
    return userEmail != null ? this.cryptoJsService.decrypt(userEmail) : "";
  }

  public getUserRole(): UserRole {
    const userRole = sessionStorage.getItem(this.USER_ROLE);
    return userRole != null ? UserRole.parse(this.cryptoJsService.decrypt(userRole)) : UserRole.NONE;
  }

  public isAuthenticated(): boolean {
    const jwtToken = sessionStorage.getItem(this.JWT_TOKEN);
    const userEmail = sessionStorage.getItem(this.USER_EMAIL);
    const userRole = sessionStorage.getItem(this.USER_ROLE);
    return jwtToken != null && userEmail != null && userRole != null;
  }

  public getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.getAuthToken(),
    });
  }

  private getAuthToken(): string {
    const jwtToken = sessionStorage.getItem(this.JWT_TOKEN);
    return "Bearer " + jwtToken;
  }

}
