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

  const REQUESTED_TRANSLATION = "W trakcie zgłoszenia";
  const ACCEPTED_TRANSLATION = "Przyjęto zgłoszenie";
  const REJECTED_TRANSLATION = "Odrzucono zgłoszenie";
  const PASSED_TRANSLATION = "Zrealizowano";
  const FAILED_TRANSLATION = "Niezrealizowano";

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
