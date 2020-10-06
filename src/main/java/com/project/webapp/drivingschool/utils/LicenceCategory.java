package com.project.webapp.drivingschool.utils;

/**
 * Kategoria prawa jazdy oferowana w szkole nauce jazdy
 */
public enum LicenceCategory {

    /**
     * Kat. AM:
     * motorower, czterokołowiec lekki (wymagane 14 lat, pisemna zgoda rodziców)
     */
    AM (14, 1000, 30, 10),

    /**
     * Kat. A1:
     * motocykl o mocy poniżej pierwszego progu (wymagane 16 lat, pisemna zgoda rodziców)
     */
    A1 (16, 1300, 30, 30),

    /**
     * Kat. A2:
     * motocykl o mocy poniżej drugiego progu (wymagane 18 lat)
     */
    A2 (18, 1400, 30, 30),

    /**
     * Kat. A:
     * motocykl bez ograniczeń (wymagane 24 lata)
     */
    A (24, 1500, 30, 30),

    /**
     * Kat. B:
     * pojazd samochodowy z przyczepą lekką (wymagane 18 lat)
     */
    B (18, 1800, 30, 30);

    /**
     * Minimalny wiek wymagany do uzyskania kategorii
     */
    public final Integer requiredAge;

    /**
     * Cena kursu
     */
    public final Integer price;

    /**
     * Liczba godzin zajęć teoretycznych (wykładów)
     */
    public final Integer theoryHours;

    /**
     * Liczba godzin zajęć praktycznych (jazd szkoleniowych)
     */
    public final Integer practiceHours;

    LicenceCategory(Integer requiredAge, Integer price, Integer theoryHours, Integer practiceHours) {
        this.requiredAge = requiredAge;
        this.price = price;
        this.theoryHours = theoryHours;
        this.practiceHours = practiceHours;
    }

}
