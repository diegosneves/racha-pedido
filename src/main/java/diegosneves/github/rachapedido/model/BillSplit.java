package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BillSplit {

    private List<Invoice> invoices;
    private Double totalPayable;

}
