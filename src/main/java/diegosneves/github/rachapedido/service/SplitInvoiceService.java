package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import diegosneves.github.rachapedido.service.contract.PersonServiceContract;
import diegosneves.github.rachapedido.service.contract.SplitInvoiceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new SplitInvoiceResponse();
    }
}
