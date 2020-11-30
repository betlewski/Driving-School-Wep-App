/**
 * Status przetworzenia dokumentów / płatności
 */
export enum ProcessingStatus {

  TO_COMPLETE,
  REQUESTED,
  COMPLETED

}

export namespace ProcessingStatus {

  const TO_COMPLETE_TRANSLATION = "Do uregulowania";
  const REQUESTED_TRANSLATION = "Zgłoszony do regulacji";
  const COMPLETED_TRANSLATION = "Uregulowany";

  export function translate(status: ProcessingStatus): string {
    let translator: string;
    switch (ProcessingStatus[status] as unknown) {
      case ProcessingStatus.TO_COMPLETE:
        translator = TO_COMPLETE_TRANSLATION;
        break;
      case ProcessingStatus.REQUESTED:
        translator = REQUESTED_TRANSLATION;
        break;
      case ProcessingStatus.COMPLETED:
        translator = COMPLETED_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

  export function getFromTranslation(status: string): ProcessingStatus | null {
    let processingStatus = null;
    switch (status) {
      case TO_COMPLETE_TRANSLATION:
        processingStatus = ProcessingStatus.TO_COMPLETE;
        break;
      case REQUESTED_TRANSLATION:
        processingStatus = ProcessingStatus.REQUESTED;
        break;
      case COMPLETED_TRANSLATION:
        processingStatus = ProcessingStatus.COMPLETED;
        break;
    }
    return processingStatus;
  }

}
