package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.mapper.BuilderMapper;
import diegosneves.github.rachapedido.mapper.InvoiceFromPersonMapper;
import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.utils.RoundUtil;

/**
 * Classe de estratégia de desconto que aplica um desconto {@link DiscountType#PERCENTAGE percentual} no total da fatura.
 * Estende a classe {@link DiscountStrategy}.<br>
 * Método para calcular o desconto para uma pessoa baseado em um valor de desconto (%), tipo de desconto e o total da fatura.<br>
 * @author diegosneves
 */
public class PercentageDiscountStrategy extends DiscountStrategy {

    @Override
    public Invoice calculateDiscount(Person person, Double discountAmount, DiscountType type, Double total, Double deliveryFee) {
        if (DiscountType.PERCENTAGE.name().equals(type.name())) {
            Double consumption = person.getItems().stream().mapToDouble(Item::getPrice).sum();
            InvoiceFromPersonMapper mapper = new InvoiceFromPersonMapper();
            Double percentageConsumedTotalBill = consumption / total;
            Invoice newInvoice = BuilderMapper.builderMapper(Invoice.class, person, mapper);
            newInvoice.setPercentageConsumedTotalBill(percentageConsumedTotalBill);
            newInvoice.setTotalPayable(RoundUtil.round((total - (total * type.discountAmount(discountAmount)) + deliveryFee) * percentageConsumedTotalBill));
            return newInvoice;
        }
        return this.checkNext(person, discountAmount, type, total, deliveryFee);
    }

}
