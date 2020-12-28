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

  public static readonly CHANGE_PWD_INCOMPLETE_DATA = "Należy uzupełnić wszystkie dane.";
  public static readonly CHANGE_PWD_NOT_EQUALS_NEW = "Nowe hasło nie zostało poprawnie potwierdzone.";
  public static readonly CHANGE_PWD_WEAK_NEW = "Nowe hasło jest za słabe.";
  public static readonly CHANGE_PWD_INVALID_OLD = "Obecne hasło jest niewłaściwe.";
  public static readonly CHANGE_PWD_SUCCESSFUL = "Hasło zostało pomyślnie zmienione.";

  public static readonly LECTURE_SERIES_INIT = "Aby móc utworzyć nowy cykl, liczba godzin wykładów, które się na niego składają, musi być równa wymaganej.";
  public static readonly LECTURE_SERIES_INCOMPLETE_DATA = "Należy uzupełnić wszystkie dane.";
  public static readonly LECTURE_SERIES_CORRECT_HOURS = "Cykl wykładów spełnia wymagania, może zostać utworzony.";
  public static readonly LECTURE_SERIES_SUCCESSFUL = "Cykl wykładów został pomyślnie utworzony.";
  public static readonly LECTURE_SERIES_ERROR = "Wystąpiły problemy podczas tworzenia cyklu wykładów. Spróbuj ponownie!";

}
