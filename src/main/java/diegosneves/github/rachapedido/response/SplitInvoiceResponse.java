package diegosneves.github.rachapedido.response;

import diegosneves.github.rachapedido.model.Invoice;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SplitInvoiceResponse {
    private List<Invoice> invoices;
    private Double totalPayable;
}
