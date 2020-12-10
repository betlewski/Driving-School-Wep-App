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

  export function shortTranslate(type: ExamType): string {
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

  export function values(): ExamType[] {
    return [ExamType.THEORETICAL, ExamType.PRACTICAL];
  }

}
