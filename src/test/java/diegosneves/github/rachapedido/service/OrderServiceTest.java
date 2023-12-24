package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.exceptions.CloseOrderException;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.Order;
import diegosneves.github.rachapedido.model.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    private static final String NULL_CONSTANT = "A lista de consumidores está nula, verifique se foram adicionados consumidores à lista.";
    private static final String ORDER_CLOSE_FAILURE_MESSAGE = "Ao processar os cálculos do pedido, ocorreu um erro.";

    @InjectMocks
    private OrderService service;

    private Person personI;
    private Person personII;
    private Item itemI;

    @BeforeEach
    void setUp() {
        this.itemI = Item.builder()
                .name("Item I")
                .price(40.0)
                .build();

        Item itemII = Item.builder()
                .name("Item II")
                .price(2.0)
                .build();

        Item itemIII = Item.builder()
                .name("Item III")
                .price(8.0)
                .build();

        this.personI = Person.builder()
                .personName("Buyer - Person I")
                .isBuyer(Boolean.TRUE)
                .email("buyer@teste.com")
                .items(List.of(this.itemI, itemII))
                .build();

        this.personII = Person.builder()
                .personName("Consumer - Person II")
                .email("consumer@teste.com")
                .items(List.of(itemIII))
                .build();
    }

    @Test
    void whenCloseOrderReceiveConsumerListThenReturnOrderListWithTotalValueConsumedPerConsumer(){
        List<Order> orders = this.service.closeOrder(List.of(this.personI, this.personII));

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertEquals("Buyer - Person I", orders.get(0).getConsumerName());
        assertEquals("Consumer - Person II", orders.get(1).getConsumerName());
        assertEquals(42.0, orders.get(0).getValueConsumed());
        assertEquals(8.0, orders.get(1).getValueConsumed());
    }

    @Test
    @SneakyThrows
    void whentakeOrdersPerConsumersReceivePersonThenReturnOrderWithTotalValueConsumedAndConsumerName(){
        Method method = this.service.getClass().getDeclaredMethod("takeOrdersPerConsumers", Person.class);
        method.setAccessible(true);

        Order order = (Order) method.invoke(this.service, this.personI);

        assertNotNull(order);
        assertEquals("Buyer - Person I", order.getConsumerName());
        assertEquals(42.0, order.getValueConsumed());
    }

    @Test
    @SneakyThrows
    void whentakeOrdersPerConsumersReceivePersonWithItemPriceNullThenThrowsNullPriceException(){
        this.itemI.setPrice(null);

        Method method = this.service.getClass().getDeclaredMethod("takeOrdersPerConsumers", Person.class);
        method.setAccessible(true);


        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> method.invoke(this.service, this.personI));
        Throwable realException = exception.getTargetException();

        assertInstanceOf(CloseOrderException.class, realException);
        assertEquals(CloseOrderException.ERROR.errorMessage(ORDER_CLOSE_FAILURE_MESSAGE), realException.getMessage());
    }

    @Test
    void whenCloseOrderReceiveConsumerListNullThenThrowsCloseOrderException(){

        Exception exception = assertThrows(CloseOrderException.class, () -> this.service.closeOrder(null));

        assertInstanceOf(CloseOrderException.class, exception);
        assertEquals(CloseOrderException.ERROR.errorMessage(NULL_CONSTANT), exception.getMessage());
    }

}
