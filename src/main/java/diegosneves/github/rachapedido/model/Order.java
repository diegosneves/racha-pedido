package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe {@link Order} representa um pedido feito por um {@link Person consumidor}.
 * Cada pedido contém o {@link String nome do consumidor} e o {@link Double valor total consumido}.
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {

    private String consumerName;
    private String email;
    private Boolean isBuyer = Boolean.FALSE;
    private Double valueConsumed;
    private List<Item> items = new ArrayList<>();

}
