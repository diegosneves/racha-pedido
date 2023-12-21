package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.model.Order;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import diegosneves.github.rachapedido.service.contract.PersonServiceContract;
import diegosneves.github.rachapedido.service.contract.SplitInvoiceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SplitInvoiceService implements SplitInvoiceServiceContract {

    private final OrderServiceContract orderService;
    private final PersonServiceContract personService;

    @Autowired
    public SplitInvoiceService(OrderServiceContract orderService, PersonServiceContract personService) {
        this.orderService = orderService;
        this.personService = personService;
    }

    @Override
    public SplitInvoiceResponse splitInvoice(SplitInvoiceRequest request) {
        List<Person> consumers = this.personService.getConsumers(request.getBuyer(), request.getSplitInvoiceWith());
        List<Order> orders;
        return new SplitInvoiceResponse();
    }
}
