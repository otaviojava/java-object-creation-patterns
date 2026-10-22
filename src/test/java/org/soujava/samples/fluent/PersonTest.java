package org.soujava.samples.fluent;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class PersonTest {

    @Nested
    class FluentCreation {

        @Test
        void shouldCreatePersonUsingFluentApi() {
            // Given
            LocalDate birthday = LocalDate.of(1990, 5, 15);

            // When
            Person person = Person.name("John")
                    .surname("Doe")
                    .city("New York")
                    .birthday(birthday);

            // Then
            assertThat(person).isNotNull();
        }

        @Test
        void shouldPreserveName() {
            // Given
            String name = "John";

            // When
            Person person = createPerson(name, "Doe",
                    "New York", LocalDate.of(1990, 5, 15));

            // Then
            assertThat(person.name()).isEqualTo(name);
        }

        @Test
        void shouldPreserveSurname() {
            // Given
            String surname = "Doe";

            // When
            Person person = createPerson("John", surname,
                    "New York", LocalDate.of(1990, 5, 15));

            // Then
            assertThat(person.surname()).isEqualTo(surname);
        }

        @Test
        void shouldPreserveCity() {
            // Given
            String city = "New York";

            // When
            Person person = createPerson("John", "Doe",
                    city, LocalDate.of(1990, 5, 15));

            // Then
            assertThat(person.city()).isEqualTo(city);
        }

        @Test
        void shouldPreserveBirthday() {
            // Given
            LocalDate birthday = LocalDate.of(1990, 5, 15);

            // When
            Person person = createPerson("John", "Doe",
                    "New York", birthday);

            // Then
            assertThat(person.birthday()).isEqualTo(birthday);
        }
    }

    @Nested
    class RequiredAttributes {

        @Test
        void shouldRejectNullName() {
            // Given
            String name = null;

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> createPerson(
                            name, "Doe", "New York",
                            LocalDate.of(1990, 5, 15)));
        }

        @Test
        void shouldRejectNullSurname() {
            // Given
            String surname = null;

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> createPerson(
                            "John", surname, "New York",
                            LocalDate.of(1990, 5, 15)));
        }

        @Test
        void shouldRejectNullCity() {
            // Given
            String city = null;

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> createPerson(
                            "John", "Doe", city,
                            LocalDate.of(1990, 5, 15)));
        }

        @Test
        void shouldRejectNullBirthday() {
            // Given
            LocalDate birthday = null;

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> createPerson(
                            "John", "Doe", "New York",
                            birthday));
        }
    }

    private static Person createPerson(
            String name,
            String surname,
            String city,
            LocalDate birthday) {

        return Person.name(name)
                .surname(surname)
                .city(city)
                .birthday(birthday);
    }
}
