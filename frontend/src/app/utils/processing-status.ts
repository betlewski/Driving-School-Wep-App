/**
 * Status przetworzenia dokumentów / płatności
 */
export enum ProcessingStatus {

  TO_COMPLETE,
  REQUESTED,
  COMPLETED

}

export namespace ProcessingStatus {

  export function translate(status: ProcessingStatus): string {
    let translator: string;
    switch (ProcessingStatus[status] as unknown) {
      case ProcessingStatus.TO_COMPLETE:
        translator = "Do uregulowania";
        break;
      case ProcessingStatus.REQUESTED:
        translator = "Zgłoszony do regulacji";
        break;
      case ProcessingStatus.COMPLETED:
        translator = "Uregulowany";
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
