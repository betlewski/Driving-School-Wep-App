/**
 * Typ dokumentu w ramach kursu nauki jazdy.
 */
export enum DocumentType {

  MEDICAL_EXAMS,
  DOCUMENT_PKK,
  PARENT_PERMISSION

}

export namespace DocumentType {

  const MEDICAL_EXAMS_TRANSLATION = "Badania lekarskie";
  const DOCUMENT_PKK_TRANSLATION = "Profil Kandydata na Kierowcę";
  const PARENT_PERMISSION_TRANSLATION = "Oświadczenie rodzica (opiekuna)";

  export function translate(type: DocumentType): string {
    let translator: string;
    switch (DocumentType[type] as unknown) {
      case DocumentType.MEDICAL_EXAMS:
        translator = MEDICAL_EXAMS_TRANSLATION;
        break;
      case DocumentType.DOCUMENT_PKK:
        translator = DOCUMENT_PKK_TRANSLATION;
        break;
      case DocumentType.PARENT_PERMISSION:
        translator = PARENT_PERMISSION_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
