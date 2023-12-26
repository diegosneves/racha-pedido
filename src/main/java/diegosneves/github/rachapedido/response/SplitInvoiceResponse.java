package diegosneves.github.rachapedido.response;

import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.model.Invoice;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SplitInvoiceResponse {

    private List<InvoiceDTO> invoices;
    private Double totalPayable;
}
