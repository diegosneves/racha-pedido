package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Person;

/**
 * Classe abstrata da {@link DiscountStrategy estratégia de desconto} representa uma parte da cadeia de responsabilidades do design pattern.
 * <p>
 * Cada instância da estratégia de desconto pode ter uma referência para a próxima estratégia na cadeia. Se a estratégia atual não pode lidar com a solicitação de desconto,
 * ela repassa a solicitação para a próxima estratégia na cadeia.
 *
 * @author diegosneves
 */
public abstract class DiscountStrategy {

    /**
     * A próxima estratégia no encadeamento.
     */
    protected DiscountStrategy next;

    /**
     * Cria e liga uma cadeia de estratégias de desconto.
     *
     * @param first A primeira estratégia na cadeia.
     * @param chain As outras estratégias na cadeia.
     * @return A primeira estratégia na cadeia ligada a todas as outras estratégias passadas no parâmetro.
     */
    public static DiscountStrategy link(DiscountStrategy first, DiscountStrategy... chain) {
        DiscountStrategy current = first;
        for (DiscountStrategy nextLink : chain) {
            current.next = nextLink;
            current = nextLink;
        }
        return first;
    }

    /**
     * Calcula o desconto com base na estratégia atual. Se a estratégia atual não pode lidar com a solicitação,
     * ela repassará a solicitação para a próxima estratégia na cadeia.
     *
     * @param person A {@link Person pessoa} que recebe o desconto.
     * @param discountAmount A quantidade de desconto.
     * @param type O {@link DiscountType tipo} de desconto.
     * @param total O total da fatura.
     * @param deliveryFee A taxa de entrega.
     * @return Uma fatura com o desconto aplicado.
     */
    public abstract Invoice calculateDiscount(Person person, Double discountAmount, DiscountType type, Double total, Double deliveryFee);

    /**
     * Verifica se há uma próxima estratégia na cadeia. Se houver, repassa a solicitação para a próxima estratégia.
     * Se não houver, cria uma nova fatura sem desconto.
     *
     * @param person A {@link Person pessoa} que recebe o desconto.
     * @param discountAmount A quantidade de desconto.
     * @param type O {@link DiscountType tipo} de desconto.
     * @param total O total da fatura.
     * @param deliveryFee A taxa de entrega.
     * @return Uma fatura com o desconto aplicado, se houver uma próxima estratégia. Caso contrário, uma nova fatura sem desconto.
     */
    protected Invoice checkNext(Person person, Double discountAmount, DiscountType type, Double total, Double deliveryFee) {
        if (this.next == null) {
            return new Invoice();
        }
        return next.calculateDiscount(person, discountAmount, type, total, deliveryFee);
    }

}
