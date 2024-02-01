package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.mapper.BuildingStrategy;
import diegosneves.github.rachapedido.mapper.InvoiceFromPersonMapper;
import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.utils.RoundUtil;

/**
 * Classe que implementa a estratégia de não aplicar desconto.
 * Herda da classe base {@link DiscountStrategy}.<br>
 * Este método realiza o cálculo de desconto baseado em uma estratégia onde {@link DiscountType#NO_DISCOUNT nenhum desconto} é aplicado.
 * <br>
 * @author diegosneves
 */
public class NoDiscountStrategy extends DiscountStrategy {

    @Override
    public Invoice calculateDiscount(Person person, Double discountAmount, DiscountType type, Double total, Double deliveryFee) {
        if (DiscountType.NO_DISCOUNT.name().equals(type.name())) {
            Double consumption = person.getItems().stream().mapToDouble(Item::getPrice).sum();
            Double percentageConsumedTotalBill = consumption / total;
            BuildingStrategy<Invoice, Person> invoicePersonBuildingStrategy = new InvoiceFromPersonMapper();
            Invoice newInvoice = invoicePersonBuildingStrategy.mapper(person);
            newInvoice.setPercentageConsumedTotalBill(percentageConsumedTotalBill);
            newInvoice.setTotalPayable(RoundUtil.round((total - type.discountAmount(discountAmount) + deliveryFee) * percentageConsumedTotalBill));
            return newInvoice;
        }
        return this.checkNext(person, discountAmount, type, total, deliveryFee);
    }
}
