/**
 * Model danych kursanta
 */
export class Student {

  id: number | null;
  fullName: string;
  pkk: string | null;
  birthDate: string;
  address: string | null;
  phoneNumber: string | null;
  email: string;
  password: string;

  constructor(id: number | null, fullName: string, pkk: string | null, birthDate: string,
              address: string | null, phoneNumber: string | null, email: string, password: string) {
    this.id = id;
    this.fullName = fullName;
    this.pkk = pkk;
    this.birthDate = birthDate;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
  }

  public static register(fullName: string, birthDate: string, email: string, password: string) {
    return new Student(null, fullName, null, birthDate, null, null, email, password);
  }

}
