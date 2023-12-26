package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.core.BankAccount;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.BillSplit;
import diegosneves.github.rachapedido.model.Person;

import java.util.List;

/**
 * Interface de contrato do serviço de fatura.
 *
 * @author diegosneves
 */
public interface InvoiceServiceContract {

    /**
     * Gera uma fatura a partir de uma lista de {@link Person consumidores}, {@link DiscountType tipo de desconto}, valor de desconto, taxa de entrega, e a {@link BankAccount conta bancária} selecionada.
     *
     * @param consumers Lista de {@link Person pessoas} que serão os consumidores.
     * @param discountType {@link DiscountType Tipo de desconto} a ser aplicado na fatura.
     * @param discount Valor do desconto a ser aplicado na fatura.
     * @param deliveryFee Taxa de entrega a ser aplicada na fatura.
     * @param selectedBank {@link BankAccount Conta bancária} selecionada para a fatura.
     * @return Objeto BillSplit que representa a fatura gerada.
     */
    BillSplit generateInvoice(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee, BankAccount selectedBank);

}
