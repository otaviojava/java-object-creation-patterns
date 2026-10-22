package org.soujava.samples.fluent;

import java.time.LocalDate;
import java.util.Objects;

public final class Person {

    private final String name;
    private final String surname;
    private final String city;
    private final LocalDate birthday;

    private Person(String name, String surname,
                   String city, LocalDate birthday) {
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.city = Objects.requireNonNull(city);
        this.birthday = Objects.requireNonNull(birthday);
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public String city() {
        return city;
    }

    public LocalDate birthday() {
        return birthday;
    }

    public static SurnameStep name(String name) {
        return surname -> city -> birthday ->
                new Person(name, surname, city, birthday);
    }

    @FunctionalInterface
    public interface SurnameStep {
        CityStep surname(String surname);
    }

    @FunctionalInterface
    public interface CityStep {
        BirthdayStep city(String city);
    }

    @FunctionalInterface
    public interface BirthdayStep {
        Person birthday(LocalDate birthday);
    }
}