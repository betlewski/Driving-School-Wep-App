/**
 * Model danych kursanta
 */
export class Student {

  id: number | null;
  fullName: string | null;
  pkk: string | null;
  birthDate: string | null;
  address: string | null;
  phoneNumber: string | null;
  email: string | null;
  password: string | null;
  registrationDate: string | null;

  constructor(id: number | null, fullName: string | null, pkk: string | null, birthDate: string | null, address: string | null,
              phoneNumber: string | null, email: string | null, password: string | null, registrationDate: string | null) {
    this.id = id;
    this.fullName = fullName;
    this.pkk = pkk;
    this.birthDate = birthDate;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
    this.registrationDate = registrationDate;
  }

  public static register(fullName: string, birthDate: string, email: string, password: string) {
    return new Student(null, fullName, null, birthDate, null,
      null, email, password, null);
  }

  public static edit(fullName: string | null, address: string | null, phoneNumber: string | null) {
    return new Student(null, fullName, null, null, address,
      phoneNumber, null, null, null);
  }

}
