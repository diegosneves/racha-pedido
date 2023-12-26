package diegosneves.github.rachapedido.model;

import diegosneves.github.rachapedido.dto.InvoiceDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BillSplit {

    private List<InvoiceDTO> invoices = new ArrayList<>();
    private Double totalPayable;

}
