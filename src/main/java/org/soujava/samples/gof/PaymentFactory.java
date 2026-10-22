package org.soujava.samples.gof;

public interface PaymentFactory {

    PaymentProcessor createPaymentProcessor();

    RefundProcessor createRefundProcessor();
}