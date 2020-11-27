/**
 * Status przebiegu kursu
 */
export enum CourseStatus {

  MEDICAL_EXAMS,
  DOCUMENTS_SUBMISSION,
  LECTURES,
  THEORY_INTERNAL_EXAM,
  DRIVING_LESSONS,
  PRACTICAL_INTERNAL_EXAM,
  STATE_EXAMS,
  FINISHED

}

export namespace CourseStatus {

  export function translate(status: CourseStatus): string {
    let translator: string;
    switch (CourseStatus[status] as unknown) {
      case CourseStatus.MEDICAL_EXAMS:
        translator = "Badania lekarskie";
        break;
      case CourseStatus.DOCUMENTS_SUBMISSION:
        translator = "Złożenie dokumentów";
        break;
      case CourseStatus.LECTURES:
        translator = "Zajęcia teoretyczne (wykłady)";
        break;
      case CourseStatus.THEORY_INTERNAL_EXAM:
        translator = "Teoretyczny egzamin wewnętrzny";
        break;
      case CourseStatus.DRIVING_LESSONS:
        translator = "Zajęcia praktyczne (jazdy szkoleniowe)";
        break;
      case CourseStatus.PRACTICAL_INTERNAL_EXAM:
        translator = "Praktyczny egzamin wewnętrzny";
        break;
      case CourseStatus.STATE_EXAMS:
        translator = "Egzaminy państwowe";
        break;
      case CourseStatus.FINISHED:
        translator = "Odebranie prawa jazdy i zakończenie kursu";
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
