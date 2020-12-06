/**
 * Model danych pracownika szkoły jazdy.
 */
export class Employee {

  id: number;
  employeeRole: string;
  fullName: string;
  phoneNumber: string;
  email: string;
  password: string;
  registrationDate: string;

  constructor(id: number, employeeRole: string, fullName: string, phoneNumber: string,
              email: string, password: string, registrationDate: string) {
    this.id = id;
    this.employeeRole = employeeRole;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
    this.registrationDate = registrationDate;
  }

}
