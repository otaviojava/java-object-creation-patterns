package org.soujava.samples.ddd;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

    private static final Currency EUR =
            Currency.getInstance("EUR");
    private static final Currency USD =
            Currency.getInstance("USD");

    private static final UUID CUSTOMER_ID =
            UUID.fromString("12345678-1234-1234-1234-123456789012");

    private static Money price(String amount, Currency currency) {
        return Money.of(new BigDecimal(amount), currency);
    }

    private static OrderItem item(String amount) {
        return new OrderItem(
                UUID.randomUUID(),
                2,
                price(amount, EUR)
        );
    }

    private static Order createOrder() {
        return new OrderFactory().create(
                CUSTOMER_ID,
                List.of(item("50.00"))
        );
    }

    @Nested
    @DisplayName("When reading an order")
    class WhenTheReading {

        @Test
        @DisplayName("Should expose its customer")
        void shouldReturnCustomer() {
            // Given
            var order = createOrder();

            // Then
            assertThat(order.customerId())
                    .as("order customer")
                    .isEqualTo(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should expose its identifier")
        void shouldReturnIdentifier() {
            // Given
            var order = createOrder();

            // Then
            assertThat(order.id())
                    .as("order identifier")
                    .isNotNull();
        }

        @Test
        @DisplayName("Should expose its initial status")
        void shouldReturnStatus() {
            // Given
            var order = createOrder();

            // Then
            assertThat(order.status())
                    .as("order status")
                    .isEqualTo(Order.Status.CREATED);
        }

        @Test
        @DisplayName("Should expose an immutable view of items")
        void shouldProtectItems() {
            // Given
            var order = createOrder();
            var items = order.items();

            // Then
            assertThatExceptionOfType(UnsupportedOperationException.class)
                    .isThrownBy(items::clear);
        }
    }

    @Nested
    @DisplayName("When adding an item")
    class WhenTheAddition {

        @Test
        @DisplayName("Should add a valid item")
        void shouldAddItem() {
            // Given
            var order = createOrder();
            var productId = UUID.randomUUID();
            var unitPrice = price("20.00", EUR);

            // When
            order.addItem(productId, 3, unitPrice);

            // Then
            assertThat(order.items())
                    .as("order items")
                    .hasSize(2);
        }

        @Test
        @DisplayName("Should recalculate the total")
        void shouldRecalculateTotal() {
            // Given
            var order = createOrder();
            var unitPrice = price("20.00", EUR);

            // When
            order.addItem(UUID.randomUUID(), 3, unitPrice);

            // Then
            assertThat(order.total().amount())
                    .as("updated order total")
                    .isEqualByComparingTo("160.00");
        }

        @Test
        @DisplayName("Should reject incompatible currency")
        void shouldRejectDifferentCurrency() {
            // Given
            var order = createOrder();
            var unitPrice = price("20.00", USD);

            // Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> order.addItem(
                            UUID.randomUUID(), 1, unitPrice))
                    .withMessage("All items must use the same currency");
        }

        @Test
        @DisplayName("Should preserve total when addition fails")
        void shouldPreserveTotalOnFailure() {
            // Given
            var order = createOrder();
            var originalTotal = order.total();
            var unitPrice = price("20.00", USD);

            // When
            try {
                order.addItem(UUID.randomUUID(), 1, unitPrice);
            } catch (IllegalArgumentException ignored) {
                // Expected rejection
            }

            // Then
            assertThat(order.total())
                    .as("unchanged total")
                    .isEqualTo(originalTotal);
        }

        @Test
        @DisplayName("Should preserve items when addition fails")
        void shouldPreserveItemsOnFailure() {
            // Given
            var order = createOrder();
            var originalItems = order.items();
            var unitPrice = price("20.00", USD);

            // When
            try {
                order.addItem(UUID.randomUUID(), 1, unitPrice);
            } catch (IllegalArgumentException ignored) {
                // Expected rejection
            }

            // Then
            assertThat(order.items())
                    .as("unchanged order items")
                    .containsExactlyElementsOf(originalItems);
        }

        @Test
        @DisplayName("Should reject invalid quantity")
        void shouldRejectInvalidQuantity() {
            // Given
            var order = createOrder();
            var unitPrice = price("20.00", EUR);

            // Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> order.addItem(
                            UUID.randomUUID(), 0, unitPrice))
                    .withMessage("Quantity must be positive");
        }

        @Test
        @DisplayName("Should reject null product ID")
        void shouldRejectNullProductId() {
            // Given
            var order = createOrder();

            // Then
            assertThatNullPointerException()
                    .isThrownBy(() -> order.addItem(
                            null, 1, price("20.00", EUR)))
                    .withMessage("Product ID is required");
        }

        @Test
        @DisplayName("Should reject null unit price")
        void shouldRejectNullUnitPrice() {
            // Given
            var order = createOrder();

            // Then
            assertThatNullPointerException()
                    .isThrownBy(() -> order.addItem(
                            UUID.randomUUID(), 1, null))
                    .withMessage("Unit price is required");
        }
    }
}
