package org.soujava.samples.ddd;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class MoneyTest {

    private static final Currency EUR =
            Currency.getInstance("EUR");
    private static final Currency USD =
            Currency.getInstance("USD");

    @Nested
    @DisplayName("When creating money")
    class WhenTheCreation {

        @Test
        @DisplayName("Should create money with valid values")
        void shouldCreate() {
            // Given
            var amount = new BigDecimal("49.90");

            // When
            var money = Money.of(amount, EUR);

            // Then
            assertSoftly(softly -> {
                softly.assertThat(money.amount())
                        .as("amount")
                        .isEqualByComparingTo(amount);
                softly.assertThat(money.currency())
                        .as("currency")
                        .isEqualTo(EUR);
            });
        }

        @Test
        @DisplayName("Should create zero money")
        void shouldCreateZero() {
            // When
            var money = Money.zero(EUR);

            // Then
            assertThat(money.amount())
                    .as("zero amount")
                    .isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    @Nested
    @DisplayName("When validating money")
    class WhenTheValidation {

        @Test
        @DisplayName("Should reject null amount")
        void shouldRejectNullAmount() {
            assertThatNullPointerException()
                    .isThrownBy(() -> Money.of(null, EUR))
                    .withMessage("Amount is required");
        }

        @Test
        @DisplayName("Should reject null currency")
        void shouldRejectNullCurrency() {
            assertThatNullPointerException()
                    .isThrownBy(() -> Money.of(
                            BigDecimal.TEN, null))
                    .withMessage("Currency is required");
        }

        @Test
        @DisplayName("Should reject null currency for zero money")
        void shouldRejectNullCurrencyForZero() {
            assertThatNullPointerException()
                    .isThrownBy(() -> Money.zero(null))
                    .withMessage("Currency is required");
        }
    }

    @Nested
    @DisplayName("When adding money")
    class WhenTheAddition {

        @Test
        @DisplayName("Should add amounts with the same currency")
        void shouldAdd() {
            // Given
            var first = Money.of(
                    new BigDecimal("10.50"), EUR);
            var second = Money.of(
                    new BigDecimal("20.25"), EUR);

            // When
            var result = first.add(second);

            // Then
            assertThat(result.amount())
                    .as("sum")
                    .isEqualByComparingTo("30.75");
        }

        @Test
        @DisplayName("Should reject different currencies")
        void shouldRejectDifferentCurrencies() {
            // Given
            var euros = Money.of(BigDecimal.TEN, EUR);
            var dollars = Money.of(BigDecimal.TEN, USD);

            // Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> euros.add(dollars))
                    .withMessage("Cannot add different currencies");
        }

        @Test
        @DisplayName("Should reject null money")
        void shouldRejectNullMoney() {
            // Given
            var money = Money.of(BigDecimal.TEN, EUR);

            // Then
            assertThatNullPointerException()
                    .isThrownBy(() -> money.add(null))
                    .withMessage("Money is required");
        }
    }

    @Nested
    @DisplayName("When multiplying money")
    class WhenTheMultiplication {

        @Test
        @DisplayName("Should multiply amount by quantity")
        void shouldMultiply() {
            // Given
            var money = Money.of(
                    new BigDecimal("19.90"), EUR);

            // When
            var result = money.multiply(3);

            // Then
            assertThat(result.amount())
                    .as("multiplied amount")
                    .isEqualByComparingTo("59.70");
        }
    }
}
