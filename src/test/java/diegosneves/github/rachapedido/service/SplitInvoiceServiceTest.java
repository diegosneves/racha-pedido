package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.core.BankAccount;
import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.*;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import diegosneves.github.rachapedido.service.contract.InvoiceServiceContract;
import diegosneves.github.rachapedido.service.contract.PersonServiceContract;
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
//    private Item item;
    private PersonDTO buyer;
    private PersonDTO friend;

    private Person consumerI;
    private Person consumerII;

    private BillSplit billSplit;

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
                .selectedBank(BankAccount.NUBANK)
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

        InvoiceDTO invoiceI = InvoiceDTO.builder()
                .consumerName("Fulano")
                .totalPayable(31.92)
                .paymentLink("n/a")
                .build();

        InvoiceDTO invoiceII = InvoiceDTO.builder()
                .consumerName("Amigo")
                .totalPayable(6.08)
                .paymentLink("link")
                .build();

        this.billSplit = BillSplit.builder()
                .invoices(List.of(invoiceI, invoiceII))
                .totalPayable(38.0)
                .build();

    }

    @Test
    void whenReceivingInvoiceThenDivisionMustBeCarriedOut() {
        when(this.personService.getConsumers(this.buyer, List.of(this.friend))).thenReturn(List.of(this.consumerI, this.consumerII));
        when(this.invoiceService.generateInvoice(anyList(), eq(DiscountType.CASH), eq(20.0), eq(8.0), eq(BankAccount.NUBANK))).thenReturn(this.billSplit);

        SplitInvoiceResponse response = this.service.splitInvoice(this.request);

        verify(personService, times(1)).getConsumers(eq(buyer), eq(List.of(friend)));
        verify(invoiceService, times(1)).generateInvoice(eq(List.of(consumerI, consumerII)), eq(DiscountType.CASH), eq(20.0), eq(8.0), eq(BankAccount.NUBANK));

        assertNotNull(response);
        InvoiceDTO buyerInvoice = response.getInvoices().stream().filter(p -> p.getConsumerName().equals(this.buyer.getPersonName())).findFirst().orElse(null);
        InvoiceDTO friendInvoice = response.getInvoices().stream().filter(p -> p.getConsumerName().equals(this.friend.getPersonName())).findFirst().orElse(null);
        assertNotNull(buyerInvoice);
        assertNotNull(friendInvoice);
        assertEquals(2, response.getInvoices().size());
        assertEquals(31.92,buyerInvoice.getTotalPayable());
        assertEquals("n/a", buyerInvoice.getPaymentLink());
        assertEquals(6.08, friendInvoice.getTotalPayable());
        assertEquals("link", friendInvoice.getPaymentLink());
        assertEquals(38.0, response.getTotalPayable());
    }

}
