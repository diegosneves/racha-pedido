package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import org.springframework.stereotype.Service;

@Service
public class SplitInvoiceService {

    // TODO - Criar as regras de Negocio

    public SplitInvoiceResponse updateEmail(SplitInvoiceRequest request) {
        final String TEST_EMAIL = "teste@teste.com.br";
        request.getBuyer().setEmail(TEST_EMAIL);

        return new SplitInvoiceResponse(request.getBuyer());
    }
}
