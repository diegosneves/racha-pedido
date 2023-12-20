package diegosneves.github.rachapedido.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Item {

    private String name;
    private Double price;

}
