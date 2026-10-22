package org.soujava.samples.fluent;


import java.time.LocalDate;
import java.util.Objects;

public final class ValidatedPersonFluent {

    private ValidatedPersonFluent() {
    }

    public static Person.SurnameStep name(String name) {
        validateText(name, "Name");

        return surname -> {
            validateText(surname, "Surname");

            return city -> {
                validateText(city, "City");

                return birthday -> {
                    validateBirthday(birthday);

                    return new Person(
                            name,
                            surname,
                            city,
                            birthday
                    );
                };
            };
        };
    }

    private static void validateText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " is required");
        }
    }

    private static void validateBirthday(LocalDate birthday) {
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
    }

}
