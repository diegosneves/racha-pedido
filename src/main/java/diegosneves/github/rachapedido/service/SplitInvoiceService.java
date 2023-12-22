package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.mapper.BuilderMapper;
import diegosneves.github.rachapedido.model.BillSplit;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.contract.InvoiceServiceContract;
import diegosneves.github.rachapedido.service.contract.PersonServiceContract;
import diegosneves.github.rachapedido.service.contract.SplitInvoiceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SplitInvoiceService implements SplitInvoiceServiceContract {

    private final PersonServiceContract personService;
    private final InvoiceServiceContract invoiceService;

    @Autowired
    public SplitInvoiceService(PersonServiceContract personService, InvoiceServiceContract invoiceService) {
        this.personService = personService;
        this.invoiceService = invoiceService;
    }

    @Override
    public SplitInvoiceResponse splitInvoice(SplitInvoiceRequest request) {
        List<Person> consumers = this.personService.getConsumers(request.getBuyer(), request.getSplitInvoiceWith());
        BillSplit billSplit = this.invoiceService.generateInvoice(consumers, request.getDiscountType(), request.getDiscount(), request.getDeliveryFee());

        return BuilderMapper.builderMapper(SplitInvoiceResponse.class, billSplit);
    }
}
