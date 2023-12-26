package diegosneves.github.rachapedido.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BankAccount {

    @JsonProperty(value = "nubank")
    NUBANK("NuBank", "https://nubank.com.br/cobrar/5h2au/658235a8-f38a-4cf5-881c-1de7114d66c7") {
        @Override
        public String paymentLink(Double amount) {
            return NUBANK.billingLink;
        }
    },
    @JsonProperty(value = "picpay")
    PICPAY("PicPay", "https://app.picpay.com/user/diego.neves215") {
        @Override
        public String paymentLink(Double amount) {
            return String.format("%s/%.2f", PICPAY.billingLink, amount);
        }
    };

    private final String bankName;
    private final String billingLink;

    BankAccount(String bankName, String billingLink) {
        this.bankName = bankName;
        this.billingLink = billingLink;
    }

    public abstract String paymentLink(Double amount);

    @Override
    public String toString() {
        return this.bankName;
    }
}
