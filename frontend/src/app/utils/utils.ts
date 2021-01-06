import {ExamType} from "./exam-type";
import {LessonStatus} from "./lesson-status";
import {Employee} from "../model/employee.model";
import {LectureSeries} from "../model/lecture-series.model";
import {Student} from "../model/student.model";
import {InternalExamRest} from "./internal-exam-rest";
import {LectureSeriesStatus} from "./lecture-series-status";

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

  public translateExamTypeByNumber(examType: ExamType | null): string {
    if (examType != null) {
      return ExamType.shortTranslateByNumber(examType);
    } else {
      return "ERROR";
    }
  }

  public translateExamTypeByText(examType: ExamType | null): string {
    if (examType != null) {
      return ExamType.shortTranslateByText(examType);
    } else {
      return "ERROR";
    }
  }

  public translateLessonStatus(status: LessonStatus | null): string {
    if (status != null) {
      return LessonStatus.translate(status);
    } else {
      return "ERROR";
    }
  }

  public translateLecturesSeriesStatus(status: LectureSeriesStatus | string | null): string {
    if (status != null) {
      return LectureSeriesStatus.translate(status);
    } else {
      return "ERROR";
    }
  }

  public convertExamIsPassedToText(isPassed: boolean | null): string {
    if (isPassed != null) {
      return isPassed ? "TAK" : "NIE";
    } else {
      return "-";
    }
  }

  public convertExamResultToText(result: number | null): string {
    if (result != null) {
      return result.toString().concat("%");
    } else {
      return "-";
    }
  }

  public convertPersonToText(person: Employee | Student | null): string {
    if (person != null && person.fullName != null && person.email) {
      return person.fullName.concat(" (")
        .concat(person.email).concat(")");
    } else {
      return "ERROR";
    }
  }

  public convertEventDateToText(date: Date | null): string {
    if (date != null) {
      const dateToParse = new Date(date);
      return dateToParse.toLocaleDateString().concat("  ")
        .concat(dateToParse.toLocaleTimeString());
    } else {
      return "ERROR";
    }
  }

  public convertTextIfEmpty(text: string): string {
    return (text != null && text.trim() != "") ? text : "-";
  }

  public convertLectureSeriesToText(series: LectureSeries | null): string {
    if (series != null && series.employee.fullName != null) {
      return "Seria nr ".concat(series.id.toString())
        .concat(" - prowadzący: ").concat(series.employee.fullName);
    } else {
      return "ERROR";
    }
  }

  public convertInternalExamRestToText(examRest: InternalExamRest): string {
    if (examRest.student.fullName != null && examRest.internalExam.id != null) {
      return "Egzamin nr ".concat(examRest.internalExam.id.toString())
        .concat(" - kursant: ").concat(examRest.student.fullName);
    } else {
      return "ERROR";
    }
  }

}
