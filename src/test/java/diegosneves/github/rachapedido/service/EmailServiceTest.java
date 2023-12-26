package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.adapter.SendEmailAdapter;
import diegosneves.github.rachapedido.model.NotificationEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService service;

    @Mock
    private SendEmailAdapter sendEmailAdapter;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenSendPaymentEmailReceiveNotificationEmailValidThenSendEmail() {
        doNothing().when(this.sendEmailAdapter).sendNotificationEmail(any(NotificationEmail.class));

        service.sendPaymentEmail(new NotificationEmail());

        verify(this.sendEmailAdapter, times(1)).sendNotificationEmail(any(NotificationEmail.class));

    }
}
