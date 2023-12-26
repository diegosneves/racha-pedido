package diegosneves.github.rachapedido.model;

import lombok.*;

/**
 * Representa um Item.
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Item {

    private String name;
    private Double price;

}
