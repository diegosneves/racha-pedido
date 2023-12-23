package diegosneves.github.rachapedido.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceDTO {

    private String consumerName;
    private Double valueConsumed;
    private Double totalPayable;
    private Double percentageConsumedTotalBill;

}
