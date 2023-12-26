package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Invoice {

    private String consumerName;
    private String email;
    private Boolean isBuyer = Boolean.FALSE;
    private List<Item> items = new ArrayList<>();
    private Double totalPayable;
    private Double percentageConsumedTotalBill;
    private String paymentLink;

}
