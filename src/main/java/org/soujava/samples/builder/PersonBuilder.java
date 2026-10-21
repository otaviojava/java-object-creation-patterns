package org.soujava.samples.builder;


import java.time.LocalDate;
import java.util.Objects;

public final class PersonBuilder {

    private String name;
    private String surname;
    private String city;
    private LocalDate birthday;

    PersonBuilder() {
    }

    public PersonBuilder name(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        this.name = name;
        return this;
    }

    public PersonBuilder surname(String surname) {
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname is required");
        }
        this.surname = surname;
        return this;
    }

    public PersonBuilder city(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }
        this.city = city;
        return this;
    }

    public PersonBuilder birthday(LocalDate birthday) {
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

        this.birthday = birthday;
        return this;
    }

    public Person build() {
        Objects.requireNonNull(name, "Name is required");
        Objects.requireNonNull(surname, "Surname is required");
        Objects.requireNonNull(city, "City is required");
        Objects.requireNonNull(birthday, "Birthday is required");

        return new Person(name, surname, city, birthday);
    }
}
