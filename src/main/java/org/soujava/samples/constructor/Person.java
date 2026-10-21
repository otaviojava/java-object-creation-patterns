package org.soujava.samples.constructor;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private String name;

    private String surname;

    private String city;

    private LocalDate birthday;

    /**
     *
     * @deprecated  avoid use the default constructor, use the xxx instead, only for frameworks that require a default constructor
     */
    Person() {
    }

    public Person(String name, String surname,
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

        this.name = name;
        this.surname = surname;
        this.city = city;
        this.birthday = birthday;
    }
}