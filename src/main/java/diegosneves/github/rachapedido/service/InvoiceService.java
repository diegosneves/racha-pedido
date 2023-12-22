package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.BillSplit;
import diegosneves.github.rachapedido.model.Order;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.service.contract.InvoiceServiceContract;
import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService implements InvoiceServiceContract {

    private final OrderServiceContract orderService;

    @Autowired
    public InvoiceService(OrderServiceContract orderService) {
        this.orderService = orderService;
    }

    @Override
    public BillSplit generateInvoice(List<Person> consumers, DiscountType discountType, Double discount, Double deliveryFee) {
        List<Order> closeOrder = this.orderService.closeOrder(consumers);
        return new BillSplit();
    }
}
