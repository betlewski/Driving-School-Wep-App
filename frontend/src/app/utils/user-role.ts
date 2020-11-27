/**
 * Role użytkowników
 */
export enum UserRole {

  NONE = "",
  STUDENT = "ROLE_STUDENT",
  EMPLOYEE = "ROLE_EMPLOYEE",
  ADMINISTRATOR = "ROLE_ADMINISTRATOR"

}

export namespace UserRole {

  export function parse(userRole: string): UserRole {
    if (userRole == UserRole.STUDENT) {
      return UserRole.STUDENT;
    } else if (userRole == UserRole.EMPLOYEE) {
      return UserRole.EMPLOYEE;
    } else if (userRole == UserRole.ADMINISTRATOR) {
      return UserRole.ADMINISTRATOR;
    } else {
      return UserRole.NONE;
    }
  }

}
