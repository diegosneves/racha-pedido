package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.service.contract.OrderServiceContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class InvoiceServiceTest {

    @InjectMocks
    private InvoiceService service;

    @Mock
    private OrderServiceContract orderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void passTest() {
        assertTrue(true);
    }

}
