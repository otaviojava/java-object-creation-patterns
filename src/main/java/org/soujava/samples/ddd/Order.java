package org.soujava.samples.ddd;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Order {

    public enum Status {
        CREATED, CONFIRMED, CANCELLED
    }

    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> items;
    private Money total;
    private Status status;

    Order(UUID id, UUID customerId,
          List<OrderItem> items, Money total,
          Status status) {

        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.items = List.copyOf(items);
        this.total = Objects.requireNonNull(total);
        this.status = Objects.requireNonNull(status);
    }

    public UUID id() {
        return id;
    }

    public UUID customerId() {
        return customerId;
    }

    public List<OrderItem> items() {
        return items;
    }

    public Money total() {
        return total;
    }

    public Status status() {
        return status;
    }

    public void addItem(
            UUID productId,
            int quantity,
            Money unitPrice) {

        if (status != Status.CREATED) {
            throw new IllegalStateException(
                    "Cannot add items to a confirmed order");
        }

        OrderItem item = new OrderItem(
                productId,
                quantity,
                unitPrice
        );

        if (!total.currency().equals(unitPrice.currency())) {
            throw new IllegalArgumentException(
                    "All items must use the same currency");
        }

        items.add(item);
        total = total.add(item.subtotal());
    }
}
