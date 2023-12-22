package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.exceptions.CloseOrderException;
import diegosneves.github.rachapedido.model.Order;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.service.OrderService;

import java.util.List;

/**
 * A interface {@link OrderServiceContract} representa o contrato para a classe {@link OrderService}.
 * Fornece um método para fechar o {@link Order pedido} para cada {@link Person consumidor} fornecido.
 *
 * @author diegosneves
 */
public interface OrderServiceContract {

    /**
     * Fecha o {@link Order pedido} para cada {@link Person consumidor} fornecido.
     *
     * @param allConsumers A lista de todos os {@link Person consumidores} para os quais o {@link Order pedido} precisa ser fechado.
     * @return A lista contendo os {@link Order pedidos} fechados, com o valor total consumido por cada consumidor.
     * @throws CloseOrderException se ocorrer algum erro durante o processo de finalização do pedido.
     */
    List<Order> closeOrder(List<Person> allConsumers) throws CloseOrderException;

}
