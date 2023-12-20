package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {

    private Double deliveryFee;
    private Double discount;
    private List<Item> items;

}
