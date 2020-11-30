/**
 * Typ dokumentu w ramach kursu nauki jazdy.
 */
export enum DocumentType {

  MEDICAL_EXAMS,
  DOCUMENT_PKK,
  PARENT_PERMISSION

}

export namespace DocumentType {

  export function translate(type: DocumentType): string {
    let translator: string;
    switch (DocumentType[type] as unknown) {
      case DocumentType.MEDICAL_EXAMS:
        translator = "Badania lekarskie";
        break;
      case DocumentType.DOCUMENT_PKK:
        translator = "Profil Kandydata na Kierowcę";
        break;
      case DocumentType.PARENT_PERMISSION:
        translator = "Zgoda rodziców na udział w kursie";
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
