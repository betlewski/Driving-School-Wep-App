/**
 * Status przebiegu cyklu wykładów.
 */
export enum LectureSeriesStatus {

  NEW,
  ONGOING,
  FINISHED

}

export namespace LectureSeriesStatus {

  const NEW_TRANSLATION = "Nowy";
  const ONGOING_TRANSLATION = "Trwający";
  const FINISHED_TRANSLATION = "Zakończony";

  export function translate(status: LectureSeriesStatus | string): string {
    let translator: string;
    // @ts-ignore
    switch (LectureSeriesStatus[status] as unknown) {
      case LectureSeriesStatus.NEW:
        translator = NEW_TRANSLATION;
        break;
      case LectureSeriesStatus.ONGOING:
        translator = ONGOING_TRANSLATION;
        break;
      case LectureSeriesStatus.FINISHED:
        translator = FINISHED_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  export function stringValues(): string[] {
    return ["NEW", "ONGOING", "FINISHED"];
  }

}
