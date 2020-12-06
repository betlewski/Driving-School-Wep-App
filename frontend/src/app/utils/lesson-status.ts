/**
 * Status przebiegu dowolnych zajęć w ramach kursu nauki jazdy.
 */
export enum LessonStatus {

  REQUESTED,
  ACCEPTED,
  REJECTED,
  PASSED,
  FAILED

}

export namespace LessonStatus {

  const REQUESTED_TRANSLATION = "Zgłoszenie prośby o akceptację";
  const ACCEPTED_TRANSLATION = "Zgłoszenie przyjęte";
  const REJECTED_TRANSLATION = "Zgłoszenie odrzucone";
  const PASSED_TRANSLATION = "Zajęcia zrealizowane";
  const FAILED_TRANSLATION = "Zajęcia niezrealizowane";

  export function translate(status: LessonStatus): string {
    let translator: string;
    switch (LessonStatus[status] as unknown) {
      case LessonStatus.REQUESTED:
        translator = REQUESTED_TRANSLATION;
        break;
      case LessonStatus.ACCEPTED:
        translator = ACCEPTED_TRANSLATION;
        break;
      case LessonStatus.REJECTED:
        translator = REJECTED_TRANSLATION;
        break;
      case LessonStatus.PASSED:
        translator = PASSED_TRANSLATION;
        break;
      case LessonStatus.FAILED:
        translator = FAILED_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
