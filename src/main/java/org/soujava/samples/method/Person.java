package org.soujava.samples.method;

import java.time.LocalDate;
import java.util.Objects;

public final class Person {

    private final String name;
    private final String surname;
    private final String city;
    private final LocalDate birthday;

    private Person(String name, String surname,
                   String city, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.birthday = birthday;
    }

    public static Person register(String name, String surname,
                                  String city, LocalDate birthday) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname is required");
        }

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }

        Objects.requireNonNull(birthday, "Birthday is required");

        LocalDate today = LocalDate.now();

        if (birthday.isAfter(today)) {
            throw new IllegalArgumentException(
                    "Birthday cannot be in the future");
        }

        if (birthday.isBefore(today.minusYears(200))) {
            throw new IllegalArgumentException(
                    "A person cannot be older than 200 years");
        }

        return new Person(name, surname, city, birthday);
    }
}