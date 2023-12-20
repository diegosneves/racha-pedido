package diegosneves.github.rachapedido.controller;

import diegosneves.github.rachapedido.controller.contract.SplitInvoiceControllerContract;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.SplitInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/split")
public class SplitInvoiceController implements SplitInvoiceControllerContract {

    private final SplitInvoiceService service;

    public SplitInvoiceController(@Autowired SplitInvoiceService service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<SplitInvoiceResponse> splitInvoice(SplitInvoiceRequest request) {
        SplitInvoiceResponse response = this.service.updateEmail(request); // TODO - Criar a regra de negocio
        return ResponseEntity.ok(response);
    }
}
