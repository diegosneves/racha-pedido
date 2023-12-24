package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.core.BankAccount;
import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.exceptions.CalculateInvoiceException;
import diegosneves.github.rachapedido.model.*;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class InvoiceServiceTest {

    private static final String CALCULATION_ERROR_MESSAGE = "Houve um problema ao calcular o valor total do pedido.";
    private static final String NULL_PARAMETER_ERROR_MESSAGE = "Um dos parâmetros necessários para a operação de cálculo da fatura está ausente ou nulo.";

    @InjectMocks
    private InvoiceService service;

    @Mock
    private OrderServiceContract orderService;

    @Mock
    private EmailServiceContract emailService;

    private Person consumerI;
    private Person consumerII;

    private Order orderI;
    private Order orderII;

    List<Order> orders;
    @BeforeEach
    void setUp() {
        Item itemI = Item.builder()
                .name("Hamburguer")
                .price(40.0)
                .build();

        Item itemII = Item.builder()
                .name("Sobremesa")
                .price(2.0)
                .build();

        Item itemIII = Item.builder()
                .name("Sanduíche")
                .price(8.0)
                .build();

        this.consumerI = Person.builder()
                .isBuyer(Boolean.TRUE)
                .personName("Fulano")
                .email("fulano@gmail.com")
                .items(List.of(itemI, itemII))
                .build();

        this.consumerII = Person.builder()
                .personName("Amigo")
                .email("amigo@gmail.com")
                .items(List.of(itemIII))
                .build();

        this.orderI = Order.builder()
                .consumerName("Fulano")
                .valueConsumed(42.0)
                .build();

        this.orderII = Order.builder()
                .consumerName("Amigo")
                .valueConsumed(8.0)
                .build();

        this.orders = List.of(this.orderI, this.orderII);
    }

    @Test
    void whenReceiveInvoiceDataThenReturnBillSplit() {
        String paymentLink = "https://nubank.com.br/cobrar/5h2au/658235a8-f38a-4cf5-881c-1de7114d66c7";

        when(orderService.closeOrder(List.of(this.consumerI, this.consumerII))).thenReturn(orders);


        BillSplit actual = this.service.generateInvoice(List.of(this.consumerI, this.consumerII), DiscountType.CASH, 20.0, 8.0, BankAccount.NUBANK);

        verify(this.emailService, atLeastOnce()).sendPaymentEmail(any(NotificationEmail.class));

        assertNotNull(actual);
        assertEquals(2, actual.getInvoices().size());
        assertEquals("Fulano", actual.getInvoices().get(0).getConsumerName());
        assertEquals(42.0, actual.getInvoices().get(0).getValueConsumed());
        assertEquals(31.92, actual.getInvoices().get(0).getTotalPayable());
        assertEquals(0.84, actual.getInvoices().get(0).getPercentageConsumedTotalBill());
        assertEquals(paymentLink, actual.getInvoices().get(0).getPaymentLink());
        assertEquals("Amigo", actual.getInvoices().get(1).getConsumerName());
        assertEquals(8.0, actual.getInvoices().get(1).getValueConsumed());
        assertEquals(6.08, actual.getInvoices().get(1).getTotalPayable());
        assertEquals(0.16, actual.getInvoices().get(1).getPercentageConsumedTotalBill());
        assertEquals(paymentLink, actual.getInvoices().get(1).getPaymentLink());
        assertEquals(38.0, actual.getTotalPayable());
    }


    @Test
    @SneakyThrows
    void whenConvertToInvoiceReceiveOrderThenReturnInvoice() {
        Method method = this.service.getClass().getDeclaredMethod("convertToInvoiceDTO", Order.class);
        method.setAccessible(true);

        InvoiceDTO actual = (InvoiceDTO) method.invoke(this.service, this.orderI);

        assertNotNull(actual);
        assertEquals("Fulano", actual.getConsumerName());
        assertEquals(42.0, actual.getValueConsumed());
        assertNull(actual.getTotalPayable());
        assertNull(actual.getPercentageConsumedTotalBill());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataAndDiscountTypeCashThenApplyDiscount() {
        List<Order> invoices = List.of(this.orderI, this.orderII);

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        List<Invoice> actual = (List<Invoice>) method.invoke(this.service, invoices, DiscountType.CASH, 20.0, 8.0);

        assertEquals("Fulano", actual.get(0).getConsumerName());
        assertEquals(42.0, actual.get(0).getValueConsumed());
        assertEquals(31.92, actual.get(0).getTotalPayable());
        assertEquals(0.84, actual.get(0).getPercentageConsumedTotalBill());
        assertNull(actual.get(0).getPaymentLink());
        assertEquals("Amigo", actual.get(1).getConsumerName());
        assertEquals(8.0, actual.get(1).getValueConsumed());
        assertEquals(6.08, actual.get(1).getTotalPayable());
        assertEquals(0.16, actual.get(1).getPercentageConsumedTotalBill());
        assertNull(actual.get(1).getPaymentLink());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataAndDiscountTypePercentageThenApplyDiscount() {
        List<Order> invoices = List.of(this.orderI, this.orderII);

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        List<Invoice> actual = (List<Invoice>) method.invoke(this.service, invoices, DiscountType.PERCENTAGE, 10.0, 8.0);

        assertEquals("Fulano", actual.get(0).getConsumerName());
        assertEquals(42.0, actual.get(0).getValueConsumed());
        assertEquals(44.52, actual.get(0).getTotalPayable());
        assertEquals(0.84, actual.get(0).getPercentageConsumedTotalBill());
        assertNull(actual.get(0).getPaymentLink());
        assertEquals("Amigo", actual.get(1).getConsumerName());
        assertEquals(8.0, actual.get(1).getValueConsumed());
        assertEquals(8.48, actual.get(1).getTotalPayable());
        assertEquals(0.16, actual.get(1).getPercentageConsumedTotalBill());
        assertNull(actual.get(1).getPaymentLink());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataAndDiscountTypeNoDiscountThenNotApplyDiscount() {
        List<Order> invoices = List.of(this.orderI, this.orderII);

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        List<Invoice> actual = (List<Invoice>) method.invoke(this.service, invoices, DiscountType.NO_DISCOUNT, 10.0, 8.0);

        assertEquals("Fulano", actual.get(0).getConsumerName());
        assertEquals(42.0, actual.get(0).getValueConsumed());
        assertEquals(48.72, actual.get(0).getTotalPayable());
        assertEquals(0.84, actual.get(0).getPercentageConsumedTotalBill());
        assertNull(actual.get(0).getPaymentLink());
        assertEquals("Amigo", actual.get(1).getConsumerName());
        assertEquals(8.0, actual.get(1).getValueConsumed());
        assertEquals(9.28, actual.get(1).getTotalPayable());
        assertEquals(0.16, actual.get(1).getPercentageConsumedTotalBill());
        assertNull(actual.get(1).getPaymentLink());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataWithValueConsumedNullThenThrowsCalculateInvoiceException() {
        this.orderI.setValueConsumed(null);
        List<Order> invoices = List.of(this.orderI, this.orderII);

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, invoices, DiscountType.NO_DISCOUNT, 10.0, 8.0));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(CALCULATION_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataWithConsumerListNullThenThrowsCalculateInvoiceException() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, null, DiscountType.NO_DISCOUNT, 10.0, 8.0));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataWithDiscountTypeNullThenThrowsCalculateInvoiceException() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, this.orders, null, 10.0, 8.0));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataWithDiscountNullThenThrowsCalculateInvoiceException() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, this.orders, DiscountType.CASH, null, 8.0));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataWithDeliveryFeeNullThenThrowsCalculateInvoiceException() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, this.orders, DiscountType.CASH, 10.0, null));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

}
