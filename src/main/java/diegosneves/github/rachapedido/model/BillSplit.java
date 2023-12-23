package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BillSplit {

    private List<Invoice> invoices = new ArrayList<>();
    private Double totalPayable;

}
