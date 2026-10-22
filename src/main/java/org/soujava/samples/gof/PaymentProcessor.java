package org.soujava.samples.gof;

import java.math.BigDecimal;

public interface PaymentProcessor {
    void pay(BigDecimal amount);
}
