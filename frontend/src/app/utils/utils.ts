import {ExamType} from "./exam-type";
import {LessonStatus} from "./lesson-status";
import {Employee} from "../model/employee.model";
import {LectureSeries} from "../model/lecture-series.model";

/**
 * Narzędzia do przetwarzania / tłumaczenia danych
 */
export class Utils {

  public static checkStringIfNotEmpty(data: string | null): boolean {
    return data != null && data.trim() != "";
  }

  public static checkIfEmailCorrect(email: string): boolean {
    return email == null ? false :
      email.match("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$") != null;
  }

  public static checkIfPasswordCorrect(password: string): boolean {
    return password == null ? false :
      password.match("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$") != null;
  }

  public translateExamTypeByNumber(examType: ExamType): string {
    return ExamType.shortTranslateByNumber(examType);
  }

  public translateExamTypeByText(examType: ExamType): string {
    return ExamType.shortTranslateByText(examType);
  }

  public translateLessonStatus(status: LessonStatus): string {
    return LessonStatus.translate(status);
  }

  public convertExamIsPassedToText(isPassed: boolean): string {
    if (isPassed != null) {
      return isPassed ? "TAK" : "NIE";
    } else {
      return "-";
    }
  }

  public convertExamResultToText(result: number): string {
    if (result != null) {
      return result.toString().concat("%");
    } else {
      return "-";
    }
  }

  public convertEmployeeToText(employee: Employee): string {
    return employee.fullName.concat(" (")
      .concat(employee.email).concat(")");
  }

  public convertEventDateToText(date: Date): string {
    const dateToParse = new Date(date);
    return dateToParse.toLocaleDateString().concat("  ")
      .concat(dateToParse.toLocaleTimeString());
  }

  public convertTextIfEmpty(text: string): string {
    return (text != null && text.trim() != "") ? text : "-";
  }

  public convertLectureSeriesToText(series: LectureSeries): string {
    return "Seria nr ".concat(series.id.toString())
      .concat(" - prowadzący: ").concat(series.employee.fullName);
  }

}
