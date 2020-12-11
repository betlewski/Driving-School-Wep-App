/**
 * Klasa definująca wyrażenia stałe w postaci tekstu.
 */
export class TextConstants {

  public static readonly REGISTRATION_DUPLICATED_EMAIL = "Podany adres email już istnieje.";
  public static readonly REGISTRATION_INVALID_DATA = "Podano niewłaściwe dane.";
  public static readonly REGISTRATION_INCOMPLETE_DATA = "Należy uzupełnić wszystkie dane.";
  public static readonly REGISTRATION_INVALID_EMAIL = "Podany adres email jest niewłaściwy.";
  public static readonly REGISTRATION_WEAK_PASSWORD = "Podane hasło jest za słabe.";

  public static readonly COURSE_INIT_TOO_YOUNG = "Jesteś zbyt młody, aby rozpocząć wybrany kurs!";

  public static readonly LESSON_NEW_SUCCESSFUL = "Zgłoszenie znajdziesz w tabeli poniżej - zaczekaj, aż prowadzący je zaakceptuje.";
  public static readonly LESSON_NEW_INVALID_TIME = "Czas rozpoczęcia (najwcześniej 6:00) musi następować po czasie zakończenia (najpóźniej 20:00)";
  public static readonly LESSON_NEW_INCOMPLETE_DATA = "Nie wybrano wszystkich danych.";

}
