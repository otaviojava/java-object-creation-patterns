package org.soujava.samples.method;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class PersonTest {

    private static final String NAME = "John";
    private static final String SURNAME = "Doe";
    private static final String CITY = "New York";
    private static final LocalDate BIRTHDAY =
            LocalDate.of(1990, 5, 15);

    @Nested
    @DisplayName("When creating a person")
    class WhenTheCreation {

        @Test
        @DisplayName("Should create a person with valid attributes")
        void shouldCreate() {
            // When
            var person = Person.of(
                    NAME, SURNAME, CITY, BIRTHDAY);

            // Then
            assertThat(person)
                    .as("person created from valid attributes")
                    .isNotNull();
        }

        @Test
        @DisplayName("Should accept a birthday exactly 200 years ago")
        void shouldAcceptMaximumAge() {
            // Given
            var birthday = LocalDate.now().minusYears(200);

            // When
            var person = Person.of(
                    NAME, SURNAME, CITY, birthday);

            // Then
            assertThat(person)
                    .as("person with maximum permitted age")
                    .isNotNull();
        }

        @Test
        @DisplayName("Should accept today's date as birthday")
        void shouldAcceptBirthdayToday() {
            // Given
            var birthday = LocalDate.now();

            // When
            var person = Person.of(
                    NAME, SURNAME, CITY, birthday);

            // Then
            assertThat(person)
                    .as("person born today")
                    .isNotNull();
        }
    }

    @Nested
    @DisplayName("When validating person creation")
    class WhenTheValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject a null or blank name")
        void shouldRejectInvalidName(String name) {
            // Then
            assertThatIllegalArgumentException()
                    .as("invalid name is rejected")
                    .isThrownBy(() -> Person.of(
                            name, SURNAME, CITY, BIRTHDAY))
                    .withMessage("Name is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject a null or blank surname")
        void shouldRejectInvalidSurname(String surname) {
            // Then
            assertThatIllegalArgumentException()
                    .as("invalid surname is rejected")
                    .isThrownBy(() -> Person.of(
                            NAME, surname, CITY, BIRTHDAY))
                    .withMessage("Surname is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject a null or blank city")
        void shouldRejectInvalidCity(String city) {
            // Then
            assertThatIllegalArgumentException()
                    .as("invalid city is rejected")
                    .isThrownBy(() -> Person.of(
                            NAME, SURNAME, city, BIRTHDAY))
                    .withMessage("City is required");
        }

        @Test
        @DisplayName("Should reject a null birthday")
        void shouldRejectNullBirthday() {
            // Then
            assertThatNullPointerException()
                    .as("null birthday is rejected")
                    .isThrownBy(() -> Person.of(
                            NAME, SURNAME, CITY, null))
                    .withMessage("Birthday is required");
        }

        @Test
        @DisplayName("Should reject a future birthday")
        void shouldRejectFutureBirthday() {
            // Given
            var birthday = LocalDate.now().plusDays(1);

            // Then
            assertThatIllegalArgumentException()
                    .as("future birthday is rejected")
                    .isThrownBy(() -> Person.of(
                            NAME, SURNAME, CITY, birthday))
                    .withMessage("Birthday cannot be in the future");
        }

        @Test
        @DisplayName("Should reject a birthday older than 200 years")
        void shouldRejectAgeOver200() {
            // Given
            var birthday = LocalDate.now()
                    .minusYears(200)
                    .minusDays(1);

            // Then
            assertThatIllegalArgumentException()
                    .as("age greater than 200 years is rejected")
                    .isThrownBy(() -> Person.of(
                            NAME, SURNAME, CITY, birthday))
                    .withMessage(
                            "A person cannot be older than 200 years");
        }
    }
}
