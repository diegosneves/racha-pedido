package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.BillSplit;
import diegosneves.github.rachapedido.model.Person;

import java.util.List;

public interface InvoiceServiceContract {

    BillSplit generateInvoice(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee);

}
