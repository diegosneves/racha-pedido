package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.core.contract.PaymentStrategy;

public class PagBank implements PaymentStrategy {

    @Override
    public String generatedPaymentLink(Double paymentAmount) {
        return String.format("Pagar: R$%,.2f", paymentAmount);
    }

    @Override
    public void collectPaymentDetails() {
        //TODO - Escrever aqui...
    }
}
