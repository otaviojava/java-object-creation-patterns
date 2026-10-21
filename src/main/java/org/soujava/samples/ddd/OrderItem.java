package org.soujava.samples.ddd;


import java.util.Objects;
import java.util.UUID;

public record OrderItem(
        UUID productId,
        int quantity,
        Money unitPrice) {

    public OrderItem {
        Objects.requireNonNull(productId, "Product ID is required");
        Objects.requireNonNull(unitPrice, "Unit price is required");

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive");
        }

        if (unitPrice.amount().signum() < 0) {
            throw new IllegalArgumentException(
                    "Unit price cannot be negative");
        }
    }

    public Money subtotal() {
        return unitPrice.multiply(quantity);
    }
}
