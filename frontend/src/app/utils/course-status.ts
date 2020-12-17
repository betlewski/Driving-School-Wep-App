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

  const MEDICAL_EXAMS_TRANSLATION = "Badania lekarskie";
  const DOCUMENTS_SUBMISSION_TRANSLATION = "Złożenie dokumentów";
  const LECTURES_TRANSLATION = "Zajęcia teoretyczne (wykłady)";
  const THEORY_INTERNAL_EXAM_TRANSLATION = "Teoretyczny egzamin wewnętrzny";
  const DRIVING_LESSONS_TRANSLATION = "Zajęcia praktyczne (jazdy szkoleniowe)";
  const PRACTICAL_INTERNAL_EXAM_TRANSLATION = "Praktyczny egzamin wewnętrzny";
  const STATE_EXAMS_TRANSLATION = "Egzaminy państwowe";
  const FINISHED_TRANSLATION = "Odebranie prawa jazdy i zakończenie kursu";

  export function translate(status: CourseStatus): string {
    let translator: string;
    switch (CourseStatus[status] as unknown) {
      case CourseStatus.MEDICAL_EXAMS:
        translator = MEDICAL_EXAMS_TRANSLATION;
        break;
      case CourseStatus.DOCUMENTS_SUBMISSION:
        translator = DOCUMENTS_SUBMISSION_TRANSLATION;
        break;
      case CourseStatus.LECTURES:
        translator = LECTURES_TRANSLATION;
        break;
      case CourseStatus.THEORY_INTERNAL_EXAM:
        translator = THEORY_INTERNAL_EXAM_TRANSLATION;
        break;
      case CourseStatus.DRIVING_LESSONS:
        translator = DRIVING_LESSONS_TRANSLATION;
        break;
      case CourseStatus.PRACTICAL_INTERNAL_EXAM:
        translator = PRACTICAL_INTERNAL_EXAM_TRANSLATION;
        break;
      case CourseStatus.STATE_EXAMS:
        translator = STATE_EXAMS_TRANSLATION;
        break;
      case CourseStatus.FINISHED:
        translator = FINISHED_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  export function getImageNumber(status: CourseStatus): string {
    let imageNumber: string;
    switch (CourseStatus[status] as unknown) {
      case CourseStatus.MEDICAL_EXAMS:
        imageNumber = "1";
        break;
      case CourseStatus.DOCUMENTS_SUBMISSION:
        imageNumber = "2";
        break;
      case CourseStatus.LECTURES:
        imageNumber = "3";
        break;
      case CourseStatus.THEORY_INTERNAL_EXAM:
        imageNumber = "4";
        break;
      case CourseStatus.DRIVING_LESSONS:
        imageNumber = "5";
        break;
      case CourseStatus.PRACTICAL_INTERNAL_EXAM:
        imageNumber = "6";
        break;
      case CourseStatus.STATE_EXAMS:
        imageNumber = "7";
        break;
      default:
        imageNumber = "";
        break;
    }
    return imageNumber;
  }

}
