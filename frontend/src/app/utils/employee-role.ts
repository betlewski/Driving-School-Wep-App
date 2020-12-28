import {LicenseCategory} from "./license-category";
import {ExamType} from "./exam-type";

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

  const ADMINISTRATOR_TRANSLATION = "Administrator";
  const DRIVING_INSTRUCTOR_A_TRANSLATION = "Instruktor A";
  const DRIVING_INSTRUCTOR_B_TRANSLATION = "Instruktor B";
  const LECTURER_TRANSLATION = "Wykładowca";
  const DELETED_TRANSLATION = "Usunięty";

  export function translate(role: EmployeeRole): string {
    let translator: string;
    switch (role) {
      case EmployeeRole.ADMINISTRATOR:
        translator = ADMINISTRATOR_TRANSLATION;
        break;
      case EmployeeRole.DRIVING_INSTRUCTOR_A:
        translator = DRIVING_INSTRUCTOR_A_TRANSLATION;
        break;
      case EmployeeRole.DRIVING_INSTRUCTOR_B:
        translator = DRIVING_INSTRUCTOR_B_TRANSLATION;
        break;
      case EmployeeRole.LECTURER:
        translator = LECTURER_TRANSLATION;
        break;
      case EmployeeRole.DELETED:
        translator = DELETED_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  export function values(): EmployeeRole[] {
    return [EmployeeRole.ADMINISTRATOR, EmployeeRole.DRIVING_INSTRUCTOR_A,
      EmployeeRole.DRIVING_INSTRUCTOR_B, EmployeeRole.LECTURER, EmployeeRole.DELETED];
  }

  export function getEmployeeRoleForExam(category: LicenseCategory, examType: ExamType | null): EmployeeRole {
    if (examType == ExamType.THEORETICAL) {
      return EmployeeRole.LECTURER;
    } else {
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

  export function getInstructorRoleForDrivingLesson(category: LicenseCategory): EmployeeRole {
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
