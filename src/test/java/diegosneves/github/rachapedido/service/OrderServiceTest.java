package diegosneves.github.rachapedido.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void pass(){
        assertTrue(true);
    }

}
