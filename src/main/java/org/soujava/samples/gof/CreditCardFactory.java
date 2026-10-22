package org.soujava.samples.gof;

public class CreditCardFactory implements PaymentFactory {


    @Override
    public PaymentProcessor createPaymentProcessor() {
        return amount -> System.out.println("CreditCard payment: " + amount);
    }

    @Override
    public RefundProcessor createRefundProcessor() {
        return amount -> System.out.println("CreditCard refund: " + amount);
    }
}
