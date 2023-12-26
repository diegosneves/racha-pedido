package diegosneves.github.rachapedido.controller;

import diegosneves.github.rachapedido.controller.contract.SplitInvoiceControllerContract;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.SplitInvoiceService;
import diegosneves.github.rachapedido.service.contract.SplitInvoiceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A classe {@link SplitInvoiceController} é responsável por lidar com solicitações HTTP relacionadas à divisão de faturas.
 * Implementa a interface {@link SplitInvoiceControllerContract}.
 *
 * @author diegosneves
 */
@RestController
@RequestMapping("/split")
public class SplitInvoiceController implements SplitInvoiceControllerContract {

    private final SplitInvoiceServiceContract service;

    public SplitInvoiceController(@Autowired SplitInvoiceService service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<SplitInvoiceResponse> splitInvoice(SplitInvoiceRequest request) {
        SplitInvoiceResponse response = this.service.splitInvoice(request);
        return ResponseEntity.ok(response);
    }
}
