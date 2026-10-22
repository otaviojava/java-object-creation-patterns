package org.soujava.samples.gof;

import java.math.BigDecimal;

public interface RefundProcessor {
    void refund(BigDecimal amount);
}