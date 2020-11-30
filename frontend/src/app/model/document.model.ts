import {ProcessingStatus} from "../utils/processing-status";
import {DocumentType} from "../utils/document-type";

/**
 * Dokument wymagany w ramach kursu.
 */
export class Document {

  id: number;
  documentType: DocumentType;
  processingStatus: ProcessingStatus;
  submissionTime: string;

  constructor(id: number, documentType: DocumentType, processingStatus: ProcessingStatus, submissionTime: string) {
    this.id = id;
    this.documentType = documentType;
    this.processingStatus = processingStatus;
    this.submissionTime = submissionTime;
  }

}
