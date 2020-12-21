package com.project.webapp.drivingschool.data.utils;

/**
 * Rola pracownika szkoły nauki jazdy
 */
public enum EmployeeRole {

    /**
     * Administrator systemu
     */
    ADMINISTRATOR,

    /**
     * Instruktor jazd szkoleniowych dla wszystkich typów kategorii A (AM, A1, A2, A)
     */
    DRIVING_INSTRUCTOR_A,

    /**
     * Instruktor jazd szkoleniowych dla kategorii B
     */
    DRIVING_INSTRUCTOR_B,

    /**
     * Wykładowca, prowadzący zajęcia teoretyczne dla wszystkich kategorii
     */
    LECTURER,

    /**
     * Pracownik usunięty
     */
    DELETED;

    /**
     * Sprawdzanie, czy pracownik jest wykładowcą.
     *
     * @return true - jeśli tak, false - w przeciwnym razie
     */
    public boolean isLecturer() {
        return LECTURER.equals(this);
    }

    /**
     * Sprawdzanie, czy pracownik jest instruktorem jazdy.
     *
     * @return true - jeśli tak, false - w przeciwnym razie
     */
    public boolean isInstructor() {
        return DRIVING_INSTRUCTOR_A.equals(this) || DRIVING_INSTRUCTOR_B.equals(this);
    }

}
