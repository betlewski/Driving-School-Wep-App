/**
 * Model danych pracownika szkoły jazdy.
 */
export class Employee {

  id: number | null;
  employeeRole: string | null;
  fullName: string | null;
  phoneNumber: string | null;
  email: string | null;
  password: string | null;
  registrationDate: string | null;

  constructor(id: number | null, employeeRole: string | null, fullName: string | null,
              phoneNumber: string | null, email: string | null, password: string | null,
              registrationDate: string | null) {
    this.id = id;
    this.employeeRole = employeeRole;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
    this.registrationDate = registrationDate;
  }

  public static register(fullName: string, employeeRole: string, email: string, password: string) {
    return new Employee(null, employeeRole, fullName,
      null, email, password, null);
  }

  public static edit(fullName: string | null, phoneNumber: string | null) {
    return new Employee(null, null, fullName, phoneNumber,
      null, null, null);
  }

}
