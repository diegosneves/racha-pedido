package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class SplitInvoiceServiceTest {

    @InjectMocks
    private SplitInvoiceService service;
    @Mock
    private OrderService orderService;
    @Mock
    private PersonService personService;

    private SplitInvoiceRequest request;
    private Item item;
    private PersonDTO buyer;
    private PersonDTO friend;

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
    }

    @Test
    void whenReceivingInvoiceThenDivisionMustBeCarriedOut() {

        SplitInvoiceResponse response = this.service.splitInvoice(this.request);

        assertNotNull(response);
        Invoice buyerInvoice = response.getInvoices().stream().filter(p -> p.getConsumerName().equals(this.buyer.getPersonName())).findFirst().orElse(null);
        Invoice friendInvoice = response.getInvoices().stream().filter(p -> p.getConsumerName().equals(this.friend.getPersonName())).findFirst().orElse(null);
        assertNotNull(buyerInvoice);
        assertNotNull(friendInvoice);
        assertEquals(2, response.getInvoices().size());
        assertEquals(31.92,buyerInvoice.getValueConsumed() );
        assertEquals(6.08, friendInvoice.getValueConsumed());
        assertEquals(38.0, response.getTotalPayable());
    }

}
