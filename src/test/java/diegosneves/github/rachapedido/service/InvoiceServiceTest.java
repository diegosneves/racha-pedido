package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.core.BankAccount;
import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.exceptions.CalculateInvoiceException;
import diegosneves.github.rachapedido.model.*;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    public static final String PAYMENT_LINK = "https://nubank.com.br/cobrar/5h2au/658235a8-f38a-4cf5-881c-1de7114d66c7";
    public static final String VOID = "";

    @InjectMocks
    private InvoiceService service;
    @Mock
    private EmailServiceContract emailService;

    @Captor
    private ArgumentCaptor<NotificationEmail> notificationEmailCaptor;
    private Person consumerI;
    private Person consumerII;


    private Invoice invoiceI;
    private Invoice invoiceII;

    private Item itemI;
    private Item itemII;
    private Item itemIII;

    private NotificationEmail emailI;
    private NotificationEmail emailII;

    @BeforeEach
    void setUp() {
        this.itemI = Item.builder()
                .name("Hamburguer")
                .price(40.0)
                .build();

        this.itemII = Item.builder()
                .name("Sobremesa")
                .price(2.0)
                .build();

        this.itemIII = Item.builder()
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
                .isBuyer(Boolean.FALSE)
                .email("amigo@gmail.com")
                .items(List.of(itemIII))
                .build();


        this.invoiceI = Invoice.builder()
                .consumerName("Fulano")
                .email("fulano@gmail.com")
                .isBuyer(Boolean.TRUE)
                .items(List.of(itemI, itemII))
                .totalPayable(31.92)
                .percentageConsumedTotalBill(0.84)
                .paymentLink(PAYMENT_LINK)
                .build();

        this.invoiceII = Invoice.builder()
                .consumerName("Amigo")
                .email("amigo@gmail.com")
                .isBuyer(Boolean.FALSE)
                .items(List.of(itemIII))
                .totalPayable(6.08)
                .percentageConsumedTotalBill(0.16)
                .paymentLink(PAYMENT_LINK)
                .build();

        this.emailI = NotificationEmail.builder()
                .email(this.consumerI.getEmail())
                .consumerName(this.consumerI.getPersonName())
                .total(31.92)
                .itens(List.of(itemI, itemII))
                .bank(BankAccount.NUBANK.toString())
                .link(PAYMENT_LINK)
                .build();

        this.emailII = NotificationEmail.builder()
                .email(this.consumerII.getEmail())
                .consumerName(this.consumerII.getPersonName())
                .total(6.08)
                .itens(List.of(itemIII))
                .bank(BankAccount.NUBANK.toString())
                .link(PAYMENT_LINK)
                .build();


    }

    @Test
    void whenReceiveInvoiceDataThenReturnBillSplit() {

        doNothing().when(this.emailService).sendPaymentEmail(any(NotificationEmail.class));

        BillSplit actual = this.service.generateInvoice(List.of(this.consumerI, this.consumerII), DiscountType.CASH, 20.0, 8.0, BankAccount.NUBANK);

        verify(this.emailService, times(1)).sendPaymentEmail(this.notificationEmailCaptor.capture());

        assertNotNull(actual);
        assertEquals(2, actual.getInvoices().size());
        assertEquals("Fulano", actual.getInvoices().get(0).getConsumerName());
        assertNotNull(actual.getInvoices());
        assertNotNull(actual.getInvoices().get(0).getItems());
        assertNotNull(actual.getInvoices().get(1).getItems());
        assertEquals(2, actual.getInvoices().get(0).getItems().size());
        assertEquals(1, actual.getInvoices().get(1).getItems().size());
        assertEquals(31.92, actual.getInvoices().get(0).getTotalPayable());
        assertEquals(VOID, actual.getInvoices().get(0).getPaymentLink());
        assertEquals("Amigo", actual.getInvoices().get(1).getConsumerName());
        assertEquals(6.08, actual.getInvoices().get(1).getTotalPayable());
        assertEquals(PAYMENT_LINK, actual.getInvoices().get(1).getPaymentLink());
        assertEquals(38.0, actual.getTotalPayable());
        assertEquals(this.invoiceII.getConsumerName(), this.notificationEmailCaptor.getValue().getConsumerName());
        assertEquals(this.invoiceII.getEmail(), this.notificationEmailCaptor.getValue().getEmail());
        assertEquals(this.invoiceII.getTotalPayable(), this.notificationEmailCaptor.getValue().getTotal());
        assertEquals(BankAccount.NUBANK.toString(), this.notificationEmailCaptor.getValue().getBank());
        assertEquals(PAYMENT_LINK, this.notificationEmailCaptor.getValue().getLink());

    }


    @Test
    @SneakyThrows
    void whenConvertToInvoiceReceiveInvoiceThenReturnInvoiceDTO() {
        Method method = this.service.getClass().getDeclaredMethod("convertToInvoiceDTO", Invoice.class);
        method.setAccessible(true);

        InvoiceDTO actual = (InvoiceDTO) method.invoke(this.service, this.invoiceI);

        assertNotNull(actual);
        assertEquals("Fulano", actual.getConsumerName());
        assertFalse(actual.getItems().isEmpty());
        assertEquals(2, actual.getItems().size());
        assertEquals(this.itemI, actual.getItems().get(0));
        assertEquals(this.itemII, actual.getItems().get(1));
        assertEquals(31.92, actual.getTotalPayable());
        assertEquals(PAYMENT_LINK, actual.getPaymentLink());


    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataAndDiscountTypeCashThenApplyDiscount() {

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        List<Invoice> actual = (List<Invoice>) method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.CASH, 20.0, 8.0);

        assertEquals("Fulano", actual.get(0).getConsumerName());
        assertEquals(31.92, actual.get(0).getTotalPayable());
        assertEquals(0.84, actual.get(0).getPercentageConsumedTotalBill());
        assertNull(actual.get(0).getPaymentLink());
        assertEquals("Amigo", actual.get(1).getConsumerName());
        assertEquals(6.08, actual.get(1).getTotalPayable());
        assertEquals(0.16, actual.get(1).getPercentageConsumedTotalBill());
        assertNull(actual.get(1).getPaymentLink());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataAndDiscountTypePercentageThenApplyDiscount() {

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        List<Invoice> actual = (List<Invoice>) method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.PERCENTAGE, 10.0, 8.0);

        assertEquals("Fulano", actual.get(0).getConsumerName());
        assertEquals(44.52, actual.get(0).getTotalPayable());
        assertEquals(0.84, actual.get(0).getPercentageConsumedTotalBill());
        assertNull(actual.get(0).getPaymentLink());
        assertEquals("Amigo", actual.get(1).getConsumerName());
        assertEquals(8.48, actual.get(1).getTotalPayable());
        assertEquals(0.16, actual.get(1).getPercentageConsumedTotalBill());
        assertNull(actual.get(1).getPaymentLink());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceiveInvoiceDataAndDiscountTypeNoDiscountThenNotApplyDiscount() {

        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        List<Invoice> actual = (List<Invoice>) method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.NO_DISCOUNT, 10.0, 8.0);

        assertEquals("Fulano", actual.get(0).getConsumerName());
        assertEquals(48.72, actual.get(0).getTotalPayable());
        assertEquals(0.84, actual.get(0).getPercentageConsumedTotalBill());
        assertNull(actual.get(0).getPaymentLink());
        assertEquals("Amigo", actual.get(1).getConsumerName());
        assertEquals(9.28, actual.get(1).getTotalPayable());
        assertEquals(0.16, actual.get(1).getPercentageConsumedTotalBill());
        assertNull(actual.get(1).getPaymentLink());

    }

    @Test
    @SneakyThrows
    void whenCalculateDiscountReceivePersonsDataWithItemPriceNullThenThrowsCalculateInvoiceException() {
        this.itemIII.setPrice(null);
        Method method = this.service.getClass().getDeclaredMethod("calculateDiscount", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.NO_DISCOUNT, 10.0, 8.0));

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

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, List.of(this.consumerI, this.consumerII), null, 10.0, 8.0));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataWithDiscountNullThenThrowsCalculateInvoiceException() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.CASH, null, 8.0));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataWithDeliveryFeeNullThenThrowsCalculateInvoiceException() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->  method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.CASH, 10.0, null));

        assertInstanceOf(CalculateInvoiceException.class, exception.getTargetException());
        assertEquals(CalculateInvoiceException.ERROR.errorMessage(NULL_PARAMETER_ERROR_MESSAGE), exception.getTargetException().getMessage());

    }

    @Test
    @SneakyThrows
    void whenValidateParametersReceiveInvoiceDataOkThenDoNothing() {

        Method method = this.service.getClass().getDeclaredMethod("validateParameters", List.class, DiscountType.class, Double.class, Double.class);
        method.setAccessible(true);

        method.invoke(this.service, List.of(this.consumerI, this.consumerII), DiscountType.CASH, 10.0, 8.0);

    }

    @Test
    @SneakyThrows
    void whenPreparateSendingEmailNotificationReceivesInvoicesDataThenReturnNotificationEmailList() {
        Method method = this.service.getClass().getDeclaredMethod("preparateSendingEmailNotification", List.class, BankAccount.class);
        method.setAccessible(true);

        List<NotificationEmail> actual = (List<NotificationEmail>) method.invoke(this.service, List.of(this.invoiceI, this.invoiceII), BankAccount.NUBANK);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("Amigo", actual.get(0).getConsumerName());
        assertEquals("amigo@gmail.com", actual.get(0).getEmail());
        assertEquals(6.08, actual.get(0).getTotal());
        assertNotNull(actual.get(0).getItens());
        assertEquals(1, actual.get(0).getItens().size());
        assertEquals(this.itemIII, actual.get(0).getItens().get(0));
        assertEquals(BankAccount.NUBANK.toString(), actual.get(0).getBank());
        assertEquals(PAYMENT_LINK, actual.get(0).getLink());

    }

    @Test
    @SneakyThrows
    void whenStatementForPaymentValidParametersThenReturnBillSplit() {
        this.invoiceI.setPaymentLink("");
        this.invoiceII.setPaymentLink("");
        this.emailII.setLink(BankAccount.PICPAY.paymentLink(6.08));
        this.emailII.setBank(BankAccount.PICPAY.toString());

        doNothing().when(this.emailService).sendPaymentEmail(any(NotificationEmail.class));

        Method method = this.service.getClass().getDeclaredMethod("statementForPayment", List.class, BankAccount.class, List.class);
        method.setAccessible(true);

        BillSplit actual = (BillSplit) method.invoke(this.service, List.of(this.invoiceI, this.invoiceII), BankAccount.PICPAY, List.of(this.emailII));

        verify(this.emailService, times(1)).sendPaymentEmail(this.notificationEmailCaptor.capture());

        assertNotNull(actual);
        assertEquals(2, actual.getInvoices().size());
        assertEquals("Fulano", actual.getInvoices().get(0).getConsumerName());
        assertNotNull(actual.getInvoices());
        assertNotNull(actual.getInvoices().get(0).getItems());
        assertNotNull(actual.getInvoices().get(1).getItems());
        assertEquals(2, actual.getInvoices().get(0).getItems().size());
        assertEquals(1, actual.getInvoices().get(1).getItems().size());
        assertEquals(31.92, actual.getInvoices().get(0).getTotalPayable());
        assertEquals(VOID, actual.getInvoices().get(0).getPaymentLink());
        assertEquals("Amigo", actual.getInvoices().get(1).getConsumerName());
        assertEquals(6.08, actual.getInvoices().get(1).getTotalPayable());
        assertEquals(BankAccount.PICPAY.paymentLink(6.08), actual.getInvoices().get(1).getPaymentLink());
        assertEquals(38.0, actual.getTotalPayable());
        assertEquals(this.invoiceII.getConsumerName(), this.notificationEmailCaptor.getValue().getConsumerName());
        assertEquals(this.invoiceII.getEmail(), this.notificationEmailCaptor.getValue().getEmail());
        assertEquals(this.invoiceII.getTotalPayable(), this.notificationEmailCaptor.getValue().getTotal());
        assertEquals(BankAccount.PICPAY.toString(), this.notificationEmailCaptor.getValue().getBank());
        assertEquals(BankAccount.PICPAY.paymentLink(6.08), this.notificationEmailCaptor.getValue().getLink());

    }

}
