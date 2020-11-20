/**
 * Model danych kursanta
 */
export class Student {

  fullName: string;
  pkk: string | null;
  birthDate: string;
  address: string | null;
  phoneNumber: string | null;
  email: string;
  password: string;

  constructor(fullName: string, pkk: string | null, birthDate: string, address: string | null,
              phoneNumber: string | null, email: string, password: string) {
    this.fullName = fullName;
    this.pkk = pkk;
    this.birthDate = birthDate;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
  }

  public static register(fullName: string, birthDate: string, email: string, password: string) {
    return new Student(fullName, null, birthDate, null, null, email, password);
  }

}
