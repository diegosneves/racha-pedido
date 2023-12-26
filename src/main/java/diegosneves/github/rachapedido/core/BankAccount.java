package diegosneves.github.rachapedido.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeração de tipos de {@link BankAccount conta bancária} com seus respectivos links para pagamento.
 *
 * @author diegosneves
 */
public enum BankAccount {

    /**
     * Conta do banco NuBank. O método paymentLink não considera o valor para formatação do link de pagamento.
     */
    @JsonProperty(value = "nubank")
    NUBANK("NuBank", "https://nubank.com.br/cobrar/5h2au/658235a8-f38a-4cf5-881c-1de7114d66c7") {
        @Override
        public String paymentLink(Double amount) {
            return NUBANK.billingLink;
        }
    },
    /**
     * Conta do PicPay. O método paymentLink considera o valor para formatação do link de pagamento.
     */
    @JsonProperty(value = "picpay")
    PICPAY("PicPay", "https://app.picpay.com/user/diego.neves215") {
        @Override
        public String paymentLink(Double amount) {
            return String.format("%s/%.2f", PICPAY.billingLink, amount);
        }
    };

    private final String bankName;
    private final String billingLink;

    /**
     * Construtor das contas bancárias
     *
     * @param bankName é o nome do banco
     * @param billingLink é o link de pagamento do banco
     */
    BankAccount(String bankName, String billingLink) {
        this.bankName = bankName;
        this.billingLink = billingLink;
    }

    /**
     * Método abstrato para formatação do link de pagamento
     *
     * @param amount é a quantidade de dinheiro a ser pago
     * @return uma String do link de pagamento formatado
     */
    public abstract String paymentLink(Double amount);

    /**
     * Sobrescrita do método toString para retornar o nome do banco
     *
     * @return uma String com o nome do banco
     */
    @Override
    public String toString() {
        return this.bankName;
    }
}
