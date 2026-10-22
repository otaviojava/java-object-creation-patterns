package org.soujava.samples.fluent;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class ValidatedPersonFluentTest {

    private static final LocalDate VALID_BIRTHDAY =
            LocalDate.of(1990, 5, 15);

    @Nested
    class ValidCreation {

        @Test
        void shouldCreatePersonWithValidAttributes() {
            // Given
            String name = "John";

            // When
            Person person = createPerson(
                    name, "Doe", "New York", VALID_BIRTHDAY);

            // Then
            assertThat(person).isNotNull();
        }

        @Test
        void shouldPreserveName() {
            // Given
            String name = "John";

            // When
            Person person = createPerson(
                    name, "Doe", "New York", VALID_BIRTHDAY);

            // Then
            assertThat(person.name()).isEqualTo(name);
        }

        @Test
        void shouldPreserveSurname() {
            // Given
            String surname = "Doe";

            // When
            Person person = createPerson(
                    "John", surname, "New York", VALID_BIRTHDAY);

            // Then
            assertThat(person.surname()).isEqualTo(surname);
        }

        @Test
        void shouldPreserveCity() {
            // Given
            String city = "New York";

            // When
            Person person = createPerson(
                    "John", "Doe", city, VALID_BIRTHDAY);

            // Then
            assertThat(person.city()).isEqualTo(city);
        }

        @Test
        void shouldPreserveBirthday() {
            // Given
            LocalDate birthday = VALID_BIRTHDAY;

            // When
            Person person = createPerson(
                    "John", "Doe", "New York", birthday);

            // Then
            assertThat(person.birthday()).isEqualTo(birthday);
        }
    }

    @Nested
    class NameValidation {

        @Test
        void shouldRejectNullName() {
            // Given
            String name = null;

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ValidatedPersonFluent.name(name))
                    .withMessage("Name is required");
        }

        @Test
        void shouldRejectBlankName() {
            // Given
            String name = "   ";

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ValidatedPersonFluent.name(name))
                    .withMessage("Name is required");
        }
    }

    @Nested
    class SurnameValidation {

        @Test
        void shouldRejectNullSurname() {
            // Given
            String surname = null;

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ValidatedPersonFluent.name("John")
                            .surname(surname))
                    .withMessage("Surname is required");
        }

        @Test
        void shouldRejectBlankSurname() {
            // Given
            String surname = "";

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ValidatedPersonFluent.name("John")
                            .surname(surname))
                    .withMessage("Surname is required");
        }
    }

    @Nested
    class CityValidation {

        @Test
        void shouldRejectNullCity() {
            // Given
            String city = null;

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ValidatedPersonFluent.name("John")
                            .surname("Doe")
                            .city(city))
                    .withMessage("City is required");
        }

        @Test
        void shouldRejectBlankCity() {
            // Given
            String city = "   ";

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> ValidatedPersonFluent.name("John")
                            .surname("Doe")
                            .city(city))
                    .withMessage("City is required");
        }
    }

    @Nested
    class BirthdayValidation {

        @Test
        void shouldRejectNullBirthday() {
            // Given
            LocalDate birthday = null;

            // When / Then
            assertThatNullPointerException()
                    .isThrownBy(() -> createPerson(
                            "John", "Doe", "New York", birthday))
                    .withMessage("Birthday is required");
        }

        @Test
        void shouldRejectFutureBirthday() {
            // Given
            LocalDate birthday = LocalDate.now().plusDays(1);

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> createPerson(
                            "John", "Doe", "New York", birthday))
                    .withMessage("Birthday cannot be in the future");
        }

        @Test
        void shouldRejectBirthdayOlderThan200Years() {
            // Given
            LocalDate birthday = LocalDate.now().minusYears(201);

            // When / Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> createPerson(
                            "John", "Doe", "New York", birthday))
                    .withMessage("A person cannot be older than 200 years");
        }

        @Test
        void shouldAcceptBirthdayExactly200YearsAgo() {
            // Given
            LocalDate birthday = LocalDate.now().minusYears(200);

            // When
            Person person = createPerson(
                    "John", "Doe", "New York", birthday);

            // Then
            assertThat(person.birthday()).isEqualTo(birthday);
        }

        @Test
        void shouldAcceptBirthdayToday() {
            // Given
            LocalDate birthday = LocalDate.now();

            // When
            Person person = createPerson(
                    "John", "Doe", "New York", birthday);

            // Then
            assertThat(person.birthday()).isEqualTo(birthday);
        }
    }

    private static Person createPerson(
            String name,
            String surname,
            String city,
            LocalDate birthday) {

        return ValidatedPersonFluent.name(name)
                .surname(surname)
                .city(city)
                .birthday(birthday);
    }
}
