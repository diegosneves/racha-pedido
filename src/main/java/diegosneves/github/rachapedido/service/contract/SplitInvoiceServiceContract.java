package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;

public interface SplitInvoiceServiceContract {

    SplitInvoiceResponse splitInvoice(SplitInvoiceRequest request);

}
