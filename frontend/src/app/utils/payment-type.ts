/**
 * Typ płatności w ramach kursu nauki jazdy.
 */
export enum PaymentType {

  COURSE_FEE,
  EXTRA_DRIVING_LESSON

}

export namespace PaymentType {

  export function translate(type: PaymentType): string {
    let translator: string;
    switch (PaymentType[type] as unknown) {
      case PaymentType.COURSE_FEE:
        translator = "Opłata za kurs (rata)";
        break;
      case PaymentType.EXTRA_DRIVING_LESSON:
        translator = "Dodatkowe godziny jazd szkoleniowych";
        break;
      default:
        translator = "";
        break;
    }
    return translator;
  }

}
