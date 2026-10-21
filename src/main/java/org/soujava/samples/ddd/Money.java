package org.soujava.samples.ddd;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "Amount is required");
        Objects.requireNonNull(currency, "Currency is required");
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money multiply(int quantity) {
        return new Money(
                amount.multiply(BigDecimal.valueOf(quantity)),
                currency
        );
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "Money is required");

        if (!currency.equals(other.currency())) {
            throw new IllegalArgumentException(
                    "Cannot add different currencies");
        }

        return new Money(amount.add(other.amount()), currency);
    }
}
