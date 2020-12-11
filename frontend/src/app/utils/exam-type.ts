import {CourseStatus} from "./course-status";

/**
 * Typ egzaminu wewnętrznego.
 */
export enum ExamType {

  THEORETICAL,
  PRACTICAL

}

export namespace ExamType {

  const FULL_THEORETICAL_TRANSLATION = "WEWNĘTRZNY EGZAMIN TEORETYCZNY";
  const FULL_PRACTICAL_TRANSLATION = "WEWNĘTRZNY EGZAMIN PRAKTYCZNY";

  export function fullTranslate(type: ExamType): string {
    let translator: string;
    switch (ExamType[type] as unknown) {
      case ExamType.THEORETICAL:
        translator = FULL_THEORETICAL_TRANSLATION;
        break;
      case ExamType.PRACTICAL:
        translator = FULL_PRACTICAL_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  const SHORT_THEORETICAL_TRANSLATION = "TEORETYCZNY";
  const SHORT_PRACTICAL_TRANSLATION = "PRAKTYCZNY";

  export function shortTranslateByNumber(type: ExamType): string {
    let translator: string;
    switch (type) {
      case ExamType.THEORETICAL:
        translator = SHORT_THEORETICAL_TRANSLATION;
        break;
      case ExamType.PRACTICAL:
        translator = SHORT_PRACTICAL_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  export function shortTranslateByText(type: ExamType): string {
    let translator: string;
    switch (ExamType[type] as unknown) {
      case ExamType.THEORETICAL:
        translator = SHORT_THEORETICAL_TRANSLATION;
        break;
      case ExamType.PRACTICAL:
        translator = SHORT_PRACTICAL_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  export function values(): ExamType[] {
    return [ExamType.THEORETICAL, ExamType.PRACTICAL];
  }

  export function getExamTypeByCourseStatus(status: CourseStatus): ExamType | null {
    switch (CourseStatus[status] as unknown) {
      case CourseStatus.THEORY_INTERNAL_EXAM:
        return ExamType.THEORETICAL;
      case CourseStatus.PRACTICAL_INTERNAL_EXAM:
        return ExamType.PRACTICAL;
      default:
        return null;
    }
  }

}
