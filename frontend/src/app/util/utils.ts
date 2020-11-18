/**
 * Narzędzia do przetwarzania danych
 */
export class Utils {

  public static checkStringIfNotEmpty(data: string): boolean {
    return data != null && data.trim() != "";
  }

}
