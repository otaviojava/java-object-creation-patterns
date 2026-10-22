package org.soujava.samples.gof;

public final class PaymentFactoryProvider {

    private PaymentFactoryProvider() {
    }

    public static PaymentFactory create(PaymentType type) {
        return switch (type) {
            case CREDIT_CARD -> new CreditCardFactory();
            case BANK_TRANSFER -> new BankTransferFactory();
        };
    }
}