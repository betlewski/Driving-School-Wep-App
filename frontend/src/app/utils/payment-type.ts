/**
 * Typ płatności w ramach kursu nauki jazdy.
 */
export enum PaymentType {

  COURSE_FEE,
  EXTRA_DRIVING_LESSON

}

export namespace PaymentType {

  const COURSE_FEE_TRANSLATION = "Opłata za kurs";
  const EXTRA_DRIVING_LESSON_TRANSLATION = "Dodatkowe godziny jazd szkoleniowych";

  export function translate(type: PaymentType): string {
    let translator: string;
    switch (PaymentType[type] as unknown) {
      case PaymentType.COURSE_FEE:
        translator = COURSE_FEE_TRANSLATION;
        break;
      case PaymentType.EXTRA_DRIVING_LESSON:
        translator = EXTRA_DRIVING_LESSON_TRANSLATION;
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
