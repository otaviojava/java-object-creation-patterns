package org.soujava.samples.ddd;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class OrderFactoryTest {

    private static final Currency EUR =
            Currency.getInstance("EUR");
    private static final Currency USD =
            Currency.getInstance("USD");

    private static final UUID CUSTOMER_ID =
            UUID.fromString("12345678-1234-1234-1234-123456789012");

    private final OrderFactory factory = new OrderFactory();

    private static OrderItem item(
            String amount, Currency currency) {

        return new OrderItem(
                UUID.randomUUID(),
                1,
                Money.of(new BigDecimal(amount), currency)
        );
    }

    @Nested
    @DisplayName("When creating an order")
    class WhenTheCreation {

        @Test
        @DisplayName("Should create a valid order aggregate")
        void shouldCreate() {
            // Given
            var items = List.of(
                    item("79.90", EUR),
                    item("29.90", EUR)
            );

            // When
            var order = factory.create(CUSTOMER_ID, items);

            // Then
            assertSoftly(softly -> {
                softly.assertThat(order.id())
                        .as("generated identity")
                        .isNotNull();
                softly.assertThat(order.customerId())
                        .as("customer")
                        .isEqualTo(CUSTOMER_ID);
                softly.assertThat(order.status())
                        .as("initial status")
                        .isEqualTo(Order.Status.CREATED);
                softly.assertThat(order.items())
                        .as("order items")
                        .containsExactlyElementsOf(items);
            });
        }

        @Test
        @DisplayName("Should calculate the order total")
        void shouldCalculateTotal() {
            // Given
            var items = List.of(
                    new OrderItem(
                            UUID.randomUUID(),
                            2,
                            Money.of(new BigDecimal("79.90"), EUR)
                    ),
                    new OrderItem(
                            UUID.randomUUID(),
                            1,
                            Money.of(new BigDecimal("29.90"), EUR)
                    )
            );

            // When
            var order = factory.create(CUSTOMER_ID, items);

            // Then
            assertThat(order.total().amount())
                    .as("calculated order total")
                    .isEqualByComparingTo("189.70");
        }

        @Test
        @DisplayName("Should create an independent order identity")
        void shouldGenerateUniqueIdentity() {
            // Given
            var items = List.of(item("10.00", EUR));

            // When
            var first = factory.create(CUSTOMER_ID, items);
            var second = factory.create(CUSTOMER_ID, items);

            // Then
            assertThat(first.id())
                    .as("distinct order identities")
                    .isNotEqualTo(second.id());
        }

        @Test
        @DisplayName("Should protect items from changes to the source list")
        void shouldCopyItems() {
            // Given
            var items = new ArrayList<>(
                    List.of(item("10.00", EUR))
            );

            // When
            var order = factory.create(CUSTOMER_ID, items);
            items.clear();

            // Then
            assertThat(order.items())
                    .as("order retains its items")
                    .hasSize(1);
        }
    }

    @Nested
    @DisplayName("When validating order creation")
    class WhenTheValidation {

        @Test
        @DisplayName("Should reject null customer ID")
        void shouldRejectNullCustomer() {
            assertThatNullPointerException()
                    .isThrownBy(() -> factory.create(
                            null,
                            List.of(item("10.00", EUR))))
                    .withMessage("Customer ID is required");
        }

        @Test
        @DisplayName("Should reject null items")
        void shouldRejectNullItems() {
            assertThatNullPointerException()
                    .isThrownBy(() -> factory.create(
                            CUSTOMER_ID, null))
                    .withMessage("Order items are required");
        }

        @Test
        @DisplayName("Should reject an empty order")
        void shouldRejectEmptyOrder() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> factory.create(
                            CUSTOMER_ID, List.of()))
                    .withMessage("An order must contain at least one item");
        }

        @Test
        @DisplayName("Should reject an order containing a null item")
        void shouldRejectNullItem() {
            // Given
            var items = new ArrayList<OrderItem>();
            items.add(item("10.00", EUR));
            items.add(null);

            // Then
            assertThatNullPointerException()
                    .isThrownBy(() -> factory.create(
                            CUSTOMER_ID, items));
        }

        @Test
        @DisplayName("Should reject mixed currencies")
        void shouldRejectMixedCurrencies() {
            // Given
            var items = List.of(
                    item("10.00", EUR),
                    item("20.00", USD)
            );

            // Then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> factory.create(
                            CUSTOMER_ID, items))
                    .withMessage("Cannot add different currencies");
        }
    }
}
