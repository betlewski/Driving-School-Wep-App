/**
 * Typ egzaminu wewnętrznego.
 */
export enum ExamType {

  THEORETICAL,
  PRACTICAL

}

export namespace ExamType {

  const THEORETICAL_TRANSLATION = "WEWNĘTRZNY EGZAMIN TEORETYCZNY";
  const PRACTICAL_TRANSLATION = "WEWNĘTRZNY EGZAMIN PRAKTYCZNY";

  export function translate(type: ExamType): string {
    let translator: string;
    switch (ExamType[type] as unknown) {
      case ExamType.THEORETICAL:
        translator = THEORETICAL_TRANSLATION;
        break;
      case ExamType.PRACTICAL:
        translator = PRACTICAL_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
