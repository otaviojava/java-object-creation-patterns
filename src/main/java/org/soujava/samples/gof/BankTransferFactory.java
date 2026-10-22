package org.soujava.samples.gof;

public final class BankTransferFactory implements PaymentFactory {

    @Override
    public PaymentProcessor createPaymentProcessor() {
        return amount -> System.out.println("Bank transfer payment: " + amount);
    }

    @Override
    public RefundProcessor createRefundProcessor() {
        return amount -> System.out.println("Bank transfer refund: " + amount);
    }
}