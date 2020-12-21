/**
 * Role użytkowników
 */
export enum UserRole {

  NONE = "",
  STUDENT = "ROLE_STUDENT",
  LECTURER = "ROLE_LECTURER",
  INSTRUCTOR = "ROLE_INSTRUCTOR",
  ADMINISTRATOR = "ROLE_ADMINISTRATOR"

}

export namespace UserRole {

  export function parse(userRole: string): UserRole {
    if (userRole == UserRole.STUDENT) {
      return UserRole.STUDENT;
    } else if (userRole == UserRole.LECTURER) {
      return UserRole.LECTURER;
    } else if (userRole == UserRole.INSTRUCTOR) {
      return UserRole.INSTRUCTOR;
    } else if (userRole == UserRole.ADMINISTRATOR) {
      return UserRole.ADMINISTRATOR;
    } else {
      return UserRole.NONE;
    }
  }

}
