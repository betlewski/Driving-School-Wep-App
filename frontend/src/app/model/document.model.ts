import {ProcessingStatus} from "../utils/processing-status";
import {DocumentType} from "../utils/document-type";

/**
 * Dokument wymagany w ramach kursu.
 */
export class Document {

  id: number;
  type: DocumentType;
  status: ProcessingStatus;
  submissionTime: string;

  constructor(id: number, type: DocumentType, status: ProcessingStatus, submissionTime: string) {
    this.id = id;
    this.type = type;
    this.status = status;
    this.submissionTime = submissionTime;
  }

}
