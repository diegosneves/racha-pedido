package diegosneves.github.rachapedido.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BankAccount {

    @JsonProperty(value = "nubank")
    NUBANK("https://nubank.com.br/cobrar/5h2au/658235a8-f38a-4cf5-881c-1de7114d66c7"),
    @JsonProperty(value = "picpay")
    PICPAY("https://app.picpay.com/user/diego.neves215");

    private final String billingLink;

    BankAccount(String billingLink) {
        this.billingLink = billingLink;
    }

    public String paymentLink() {
        return this.billingLink;
    }

}
