/**
 * Model danych zwracanych po uwierzytelnieniu użytkownika
 */
export class JwtResponse {

  jwtToken: string;
  username: string;
  userRole: string;

  constructor(jwtToken: string, username: string, userRole: string) {
    this.jwtToken = jwtToken;
    this.username = username;
    this.userRole = userRole;
  }

}
