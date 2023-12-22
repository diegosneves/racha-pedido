package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.exceptions.CloseOrderException;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.Order;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * A classe {@link OrderService} é responsável por fechar pedidos para cada consumidor fornecido.
 * Ela implementa a interface {@link OrderServiceContract}.
 *
 * @author diegosneves
 */
@Service
public class OrderService implements OrderServiceContract {

    private static final String NULL_CONSTANT = "A lista de consumidores está nula, verifique se foram adicionados consumidores à lista.";
    private static final String ORDER_CLOSE_FAILURE_MESSAGE = "Ao processar os cálculos do pedido, ocorreu um erro.";

    @Override
    public List<Order> closeOrder(List<Person> allConsumers) throws CloseOrderException {
        if (isNull(allConsumers)) {
            throw new CloseOrderException(NULL_CONSTANT);
        }
        return allConsumers.stream().map(this::takeOrdersPerConsumers).toList();
    }

    /**
     * Este método recebe um objeto {@link Person} como parâmetro e calcula o valor total de itens associados a essa pessoa.
     * Em seguida, cria um objeto {@link Order} com o nome do consumidor e o valor total consumido e retorna esse objeto Order.
     * Caso qualquer exceção ocorra durante o cálculo, uma exceção {@link CloseOrderException} será lançada com uma mensagem de erro apropriada.
     *
     * @param person O objeto {@link Person} para o qual o pedido precisa ser criado.
     * @return Um objeto {@link Order} com o nome do consumidor e o valor total consumido.
     * @throws CloseOrderException Se ocorrer alguma exceção durante o cálculo do valor total.
     */
    private Order takeOrdersPerConsumers(Person person) throws CloseOrderException {
        Double totalValue = null;
        try {
            totalValue = person.getItems().stream()
                    .mapToDouble(Item::getPrice)
                    .sum();
        } catch (Exception e) {
            throw new CloseOrderException(ORDER_CLOSE_FAILURE_MESSAGE, e);
        }
        return Order.builder()
                .consumerName(person.getPersonName())
                .valueConsumed(totalValue)
                .build();
    }


}
