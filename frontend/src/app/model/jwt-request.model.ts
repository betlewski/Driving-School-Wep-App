/**
 * Model danych użytkownika do uwierzytelnienia
 */
export class JwtRequest {

  username: string;
  password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }

}
