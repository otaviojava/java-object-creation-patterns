package org.soujava.samples.constructor;

import java.time.LocalDate;
import java.util.Objects;

public final class PersonWithSetters {

    private String name;
    private String surname;
    private String city;
    private LocalDate birthday;

    public PersonWithSetters(String name, String surname,
                             String city, LocalDate birthday) {

        setName(name);
        setSurname(surname);
        setCity(city);
        setBirthday(birthday);
    }

    void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        this.name = name;
    }

    void setSurname(String surname) {
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname is required");
        }
        this.surname = surname;
    }

     void setCity(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }
        this.city = city;
    }

    void setBirthday(LocalDate birthday) {
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
    }
}