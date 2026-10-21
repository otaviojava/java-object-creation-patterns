package org.soujava.samples.ddd;


import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class OrderFactory {

    public Order create(UUID customerId, List<OrderItem> items) {

        Objects.requireNonNull(customerId, "Customer ID is required");
        Objects.requireNonNull(items, "Order items are required");

        if (items.isEmpty()) {
            throw new IllegalArgumentException(
                    "An order must contain at least one item");
        }

        List<OrderItem> validatedItems = List.copyOf(items);

        Currency currency = validatedItems.getFirst()
                .unitPrice()
                .currency();

        Money total = validatedItems.stream()
                .map(OrderItem::subtotal)
                .reduce(
                        Money.zero(currency),
                        Money::add
                );

        return new Order(
                UUID.randomUUID(),
                customerId,
                validatedItems,
                total,
                Order.Status.CREATED
        );
    }
}
