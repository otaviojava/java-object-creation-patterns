package org.soujava.samples.constructor;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class PersonWithSettersTest {

    private static final String NAME = "John";
    private static final String SURNAME = "Doe";
    private static final String CITY = "New York";
    private static final LocalDate BIRTHDAY =
            LocalDate.of(1990, 5, 15);

    private PersonWithSetters createPerson() {
        return new PersonWithSetters(
                NAME, SURNAME, CITY, BIRTHDAY);
    }

    @Nested
    @DisplayName("When creating a person")
    class WhenTheCreation {

        @Test
        @DisplayName("Should create a person with valid attributes")
        void shouldCreate() {
            // When
            var person = createPerson();

            // Then
            assertThat(person)
                    .as("person is created")
                    .isNotNull();
        }

        @Test
        @DisplayName("Should accept a birthday exactly 200 years ago")
        void shouldAcceptMaximumAge() {
            // Given
            var birthday = LocalDate.now().minusYears(200);

            // When
            var person = new PersonWithSetters(
                    NAME, SURNAME, CITY, birthday);

            // Then
            assertThat(person)
                    .as("person at maximum age is created")
                    .isNotNull();
        }

        @Test
        @DisplayName("Should accept today's date as birthday")
        void shouldAcceptBirthdayToday() {
            // Given
            var birthday = LocalDate.now();

            // When
            var person = new PersonWithSetters(
                    NAME, SURNAME, CITY, birthday);

            // Then
            assertThat(person)
                    .as("person born today is created")
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
            assertThatIllegalArgumentException()
                    .as("invalid name is rejected")
                    .isThrownBy(() -> new PersonWithSetters(
                            name, SURNAME, CITY, BIRTHDAY))
                    .withMessage("Name is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject a null or blank surname")
        void shouldRejectInvalidSurname(String surname) {
            assertThatIllegalArgumentException()
                    .as("invalid surname is rejected")
                    .isThrownBy(() -> new PersonWithSetters(
                            NAME, surname, CITY, BIRTHDAY))
                    .withMessage("Surname is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject a null or blank city")
        void shouldRejectInvalidCity(String city) {
            assertThatIllegalArgumentException()
                    .as("invalid city is rejected")
                    .isThrownBy(() -> new PersonWithSetters(
                            NAME, SURNAME, city, BIRTHDAY))
                    .withMessage("City is required");
        }

        @Test
        @DisplayName("Should reject a null birthday")
        void shouldRejectNullBirthday() {
            assertThatNullPointerException()
                    .as("null birthday is rejected")
                    .isThrownBy(() -> new PersonWithSetters(
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
                    .isThrownBy(() -> new PersonWithSetters(
                            NAME, SURNAME, CITY, birthday))
                    .withMessage("Birthday cannot be in the future");
        }

        @Test
        @DisplayName("Should reject a person older than 200 years")
        void shouldRejectAgeOver200() {
            // Given
            var birthday = LocalDate.now()
                    .minusYears(200)
                    .minusDays(1);

            // Then
            assertThatIllegalArgumentException()
                    .as("age over 200 years is rejected")
                    .isThrownBy(() -> new PersonWithSetters(
                            NAME, SURNAME, CITY, birthday))
                    .withMessage(
                            "A person cannot be older than 200 years");
        }
    }

    @Nested
    @DisplayName("When updating person attributes")
    class WhenTheUpdate {

        @Test
        @DisplayName("Should accept valid changes")
        void shouldUpdate() {
            // Given
            var person = createPerson();

            // Then
            assertThatCode(() -> {
                person.setName("Jane");
                person.setSurname("Smith");
                person.setCity("London");
                person.setBirthday(LocalDate.of(2000, 1, 1));
            }).as("valid updates are accepted")
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject an invalid name")
        void shouldRejectInvalidName(String name) {
            // Given
            var person = createPerson();

            // Then
            assertThatIllegalArgumentException()
                    .as("invalid name update is rejected")
                    .isThrownBy(() -> person.setName(name))
                    .withMessage("Name is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject an invalid surname")
        void shouldRejectInvalidSurname(String surname) {
            // Given
            var person = createPerson();

            // Then
            assertThatIllegalArgumentException()
                    .as("invalid surname update is rejected")
                    .isThrownBy(() -> person.setSurname(surname))
                    .withMessage("Surname is required");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Should reject an invalid city")
        void shouldRejectInvalidCity(String city) {
            // Given
            var person = createPerson();

            // Then
            assertThatIllegalArgumentException()
                    .as("invalid city update is rejected")
                    .isThrownBy(() -> person.setCity(city))
                    .withMessage("City is required");
        }

        @Test
        @DisplayName("Should reject a null birthday")
        void shouldRejectNullBirthday() {
            // Given
            var person = createPerson();

            // Then
            assertThatNullPointerException()
                    .as("null birthday update is rejected")
                    .isThrownBy(() -> person.setBirthday(null))
                    .withMessage("Birthday is required");
        }

        @Test
        @DisplayName("Should reject a future birthday")
        void shouldRejectFutureBirthday() {
            // Given
            var person = createPerson();
            var birthday = LocalDate.now().plusDays(1);

            // Then
            assertThatIllegalArgumentException()
                    .as("future birthday update is rejected")
                    .isThrownBy(() -> person.setBirthday(birthday))
                    .withMessage("Birthday cannot be in the future");
        }

        @Test
        @DisplayName("Should reject a birthday older than 200 years")
        void shouldRejectAgeOver200() {
            // Given
            var person = createPerson();
            var birthday = LocalDate.now()
                    .minusYears(200)
                    .minusDays(1);

            // Then
            assertThatIllegalArgumentException()
                    .as("age over 200 years is rejected")
                    .isThrownBy(() -> person.setBirthday(birthday))
                    .withMessage(
                            "A person cannot be older than 200 years");
        }
    }
}
