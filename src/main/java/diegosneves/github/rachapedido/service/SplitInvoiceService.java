package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.contract.SplitInvoiceServiceContract;
import org.springframework.stereotype.Service;

@Service
public class SplitInvoiceService implements SplitInvoiceServiceContract {



    @Override
    public SplitInvoiceResponse splitInvoice(SplitInvoiceRequest request) {
        final String TEST_EMAIL = "teste@teste.com.br";
        request.getBuyer().setEmail(TEST_EMAIL);

        return new SplitInvoiceResponse(request.getBuyer());
    }
}
