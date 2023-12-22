package diegosneves.github.rachapedido.model;

import lombok.*;

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
    private Double valueConsumed;

}
