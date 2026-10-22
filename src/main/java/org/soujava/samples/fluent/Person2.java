package org.soujava.samples.fluent;

import java.time.LocalDate;
import java.util.Objects;

public final class Person2 {

    private final String name;
    private final String surname;
    private final String city;
    private final LocalDate birthday;

    Person2(String name, String surname,
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
        return surname -> new CityStep() {

            @Override
            public BirthdayStep city(String city) {
                return birthday ->
                        new Person2(name, surname, city, birthday);
            }

            @Override
            public BirthdayStep withoutCity() {
                return birthday ->
                        new Person2(name, surname, null, birthday);
            }
        };
    }

    @FunctionalInterface
    public interface SurnameStep {
        CityStep surname(String surname);
    }

    public interface CityStep {
        BirthdayStep city(String city);
        BirthdayStep withoutCity();
    }

    @FunctionalInterface
    public interface BirthdayStep {
        Person2 birthday(LocalDate birthday);
    }
}