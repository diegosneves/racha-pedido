package diegosneves.github.rachapedido.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Invoice {

    private String consumerName;
    private Double valueConsumed;
    private Double percentageConsumed;

}
