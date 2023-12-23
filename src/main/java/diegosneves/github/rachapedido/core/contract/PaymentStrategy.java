package diegosneves.github.rachapedido.core.contract;

public interface PaymentStrategy {

    String generatedPaymentLink(Double paymentAmount);
    void collectPaymentDetails();
}
