import {LicenseCategory} from "./license-category";

/**
 * Role pracowników.
 */
export enum EmployeeRole {

  ADMINISTRATOR,
  DRIVING_INSTRUCTOR_A,
  DRIVING_INSTRUCTOR_B,
  LECTURER,
  DELETED,
  NONE

}

export namespace EmployeeRole {

  export function getInstructorRoleByCourseCategory(category: LicenseCategory): EmployeeRole {
    switch (category.toString()) {
      case "AM":
      case "A1":
      case "A2":
      case "A":
        return EmployeeRole.DRIVING_INSTRUCTOR_A;
      case "B":
        return EmployeeRole.DRIVING_INSTRUCTOR_B;
      default:
        return EmployeeRole.NONE;
    }
  }

}
