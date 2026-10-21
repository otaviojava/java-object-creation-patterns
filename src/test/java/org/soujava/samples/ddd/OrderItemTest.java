package org.soujava.samples.ddd;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class OrderItemTest {

    private static final UUID PRODUCT_ID =
            UUID.fromString("12345678-1234-1234-1234-123456789012");

    private static final Currency EUR =
            Currency.getInstance("EUR");

    private static Money price(String amount) {
        return Money.of(new BigDecimal(amount), EUR);
    }

    @Nested
    @DisplayName("When creating an order item")
    class WhenTheCreation {

        @Test
        @DisplayName("Should create an item with valid attributes")
        void shouldCreate() {
            // Given
            var unitPrice = price("79.90");

            // When
            var item = new OrderItem(
                    PRODUCT_ID, 2, unitPrice);

            // Then
            assertSoftly(softly -> {
                softly.assertThat(item.productId())
                        .as("product ID")
                        .isEqualTo(PRODUCT_ID);
                softly.assertThat(item.quantity())
                        .as("quantity")
                        .isEqualTo(2);
                softly.assertThat(item.unitPrice())
                        .as("unit price")
                        .isEqualTo(unitPrice);
            });
        }

        @Test
        @DisplayName("Should accept zero unit price")
        void shouldAcceptZeroPrice() {
            // When
            var item = new OrderItem(
                    PRODUCT_ID, 1, Money.zero(EUR));

            // Then
            assertThat(item.unitPrice().amount())
                    .as("zero unit price")
                    .isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    @Nested
    @DisplayName("When validating an order item")
    class WhenTheValidation {

        @Test
        @DisplayName("Should reject null product ID")
        void shouldRejectNullProductId() {
            assertThatNullPointerException()
                    .isThrownBy(() -> new OrderItem(
                            null, 1, price("10.00")))
                    .withMessage("Product ID is required");
        }

        @Test
        @DisplayName("Should reject zero quantity")
        void shouldRejectZeroQuantity() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new OrderItem(
                            PRODUCT_ID, 0, price("10.00")))
                    .withMessage("Quantity must be positive");
        }

        @Test
        @DisplayName("Should reject negative quantity")
        void shouldRejectNegativeQuantity() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new OrderItem(
                            PRODUCT_ID, -1, price("10.00")))
                    .withMessage("Quantity must be positive");
        }

        @Test
        @DisplayName("Should reject null unit price")
        void shouldRejectNullUnitPrice() {
            assertThatNullPointerException()
                    .isThrownBy(() -> new OrderItem(
                            PRODUCT_ID, 1, null))
                    .withMessage("Unit price is required");
        }

        @Test
        @DisplayName("Should reject negative unit price")
        void shouldRejectNegativePrice() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new OrderItem(
                            PRODUCT_ID, 1, price("-10.00")))
                    .withMessage("Unit price cannot be negative");
        }
    }

    @Nested
    @DisplayName("When calculating an item subtotal")
    class WhenTheCalculation {

        @Test
        @DisplayName("Should calculate subtotal from quantity and price")
        void shouldCalculateSubtotal() {
            // Given
            var item = new OrderItem(
                    PRODUCT_ID, 3, price("29.90"));

            // When
            var subtotal = item.subtotal();

            // Then
            assertThat(subtotal.amount())
                    .as("item subtotal")
                    .isEqualByComparingTo("89.70");
        }
    }
}
