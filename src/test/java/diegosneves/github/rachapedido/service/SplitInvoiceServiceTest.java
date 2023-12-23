package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.*;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.contract.InvoiceServiceContract;
import diegosneves.github.rachapedido.service.contract.PersonServiceContract;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SplitInvoiceServiceTest {

    @InjectMocks
    private SplitInvoiceService service;
    @Mock
    private InvoiceServiceContract invoiceService;
    @Mock
    private PersonServiceContract personService;

    private SplitInvoiceRequest request;
    private Item item;
    private PersonDTO buyer;
    private PersonDTO friend;

    private Person consumerI;
    private Person consumerII;

    private BillSplit billSplit;
    private Invoice invoiceI;
    private Invoice invoiceII;

    @BeforeEach
    void setUp() {
        this.buyer = PersonDTO.builder()
                .personName("Fulano")
                .email("fulano@gmail.com")
                .items(List.of(new Item("Hamburguer", 40.0), new Item("Sobremesa", 2.0)))
                .build();

        this.friend = PersonDTO.builder()
                .personName("Amigo")
                .email("amigo@gmail.com")
                .items(List.of(new Item("Sanduíche", 8.0)))
                .build();

        this.request = SplitInvoiceRequest.builder()
                .buyer(this.buyer)
                .splitInvoiceWith(List.of(this.friend))
                .deliveryFee(8.0)
                .discount(20.0)
                .discountType(DiscountType.CASH)
                .build();

        this.consumerI = Person.builder()
                .isBuyer(Boolean.TRUE)
                .personName("Fulano")
                .email("fulano@gmail.com")
                .items(List.of(new Item("Hamburguer", 40.0), new Item("Sobremesa", 2.0)))
                .build();

        this.consumerII = Person.builder()
                .personName("Amigo")
                .email("amigo@gmail.com")
                .items(List.of(new Item("Sanduíche", 8.0)))
                .build();

        this.invoiceI = Invoice.builder()
                .consumerName("Fulano")
                .valueConsumed(42.0)
                .totalPayable(31.92)
                .percentageConsumedTotalBill(84.0)
                .paymentLink("n/a")
                .build();

        this.invoiceII = Invoice.builder()
                .consumerName("Amigo")
                .valueConsumed(8.0)
                .totalPayable(6.08)
                .percentageConsumedTotalBill(16.0)
                .paymentLink("link")
                .build();

        this.billSplit = BillSplit.builder()
                .invoices(List.of(this.invoiceI, this.invoiceII))
                .totalPayable(38.0)
                .build();

    }

    @Test
    void whenReceivingInvoiceThenDivisionMustBeCarriedOut() {
        when(this.personService.getConsumers(this.buyer, List.of(this.friend))).thenReturn(List.of(this.consumerI, this.consumerII));
        when(this.invoiceService.generateInvoice(anyList(), eq(DiscountType.CASH), eq(20.0), eq(8.0))).thenReturn(this.billSplit);

        SplitInvoiceResponse response = this.service.splitInvoice(this.request);

        verify(personService, times(1)).getConsumers(eq(buyer), eq(List.of(friend)));
        verify(invoiceService, times(1)).generateInvoice(eq(List.of(consumerI, consumerII)), eq(DiscountType.CASH), eq(20.0), eq(8.0));

        assertNotNull(response);
        Invoice buyerInvoice = response.getInvoices().stream().filter(p -> p.getConsumerName().equals(this.buyer.getPersonName())).findFirst().orElse(null);
        Invoice friendInvoice = response.getInvoices().stream().filter(p -> p.getConsumerName().equals(this.friend.getPersonName())).findFirst().orElse(null);
        assertNotNull(buyerInvoice);
        assertNotNull(friendInvoice);
        assertEquals(2, response.getInvoices().size());
        assertEquals(42.0,buyerInvoice.getValueConsumed());
        assertEquals(31.92,buyerInvoice.getTotalPayable());
        assertEquals(84.0, buyerInvoice.getPercentageConsumedTotalBill());
        assertEquals("n/a", buyerInvoice.getPaymentLink());
        assertEquals(8.0, friendInvoice.getValueConsumed());
        assertEquals(6.08, friendInvoice.getTotalPayable());
        assertEquals(16.0, friendInvoice.getPercentageConsumedTotalBill());
        assertEquals("link", friendInvoice.getPaymentLink());
        assertEquals(38.0, response.getTotalPayable());
    }

}
