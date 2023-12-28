package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.adapter.SendEmailAdapter;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.NotificationEmail;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService service;

    @Mock
    private SendEmailAdapter sendEmailAdapter;

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Captor
    private ArgumentCaptor<Context> contextCaptor;

    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    private NotificationEmail notificationEmail;

    @BeforeEach
    void setUp() {
        this.notificationEmail = NotificationEmail.builder()
                .email("email@email.com")
                .consumerName("Nome")
                .total(8.0)
                .itens(List.of(Item.builder().price(9.0).name("item").build()))
                .bank("banco")
                .link("link")
                .build();
    }

    @Test
    void whenSendPaymentEmailReceiveNotificationEmailValidThenSendEmail() {
        doNothing().when(this.sendEmailAdapter).sendNotificationEmail(any(NotificationEmail.class));

        service.sendPaymentEmail(new NotificationEmail());

        verify(this.sendEmailAdapter, times(1)).sendNotificationEmail(any(NotificationEmail.class));

    }

    @Test
    @SneakyThrows
    void whenSendMailReceiveNotificationEmailValidThenSendEmail() {
        when(this.templateEngine.process(anyString(), any(Context.class))).thenReturn("html");
        when(this.emailSender.createMimeMessage()).thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        doNothing().when(this.emailSender).send(any(MimeMessage.class));

        this.service.sendEmail(this.notificationEmail);

        verify(this.templateEngine, times(1)).process(anyString(), this.contextCaptor.capture());
        verify(this.emailSender, times(1)).createMimeMessage();
        verify(this.emailSender, times(1)).send(this.mimeMessageCaptor.capture());
        verify(this.sendEmailAdapter, never()).sendNotificationEmail(any(NotificationEmail.class));

        assertEquals("email@email.com", this.mimeMessageCaptor.getValue().getAllRecipients()[0].toString());
        assertEquals("Nome", this.contextCaptor.getValue().getVariable("consumerName"));
        assertEquals(8.0, this.contextCaptor.getValue().getVariable("total"));
        assertEquals(1, ((List<Item>) this.contextCaptor.getValue().getVariable("itens")).size());
        assertEquals("banco", this.contextCaptor.getValue().getVariable("bank"));
        assertEquals("link", this.contextCaptor.getValue().getVariable("link"));

    }

    @Test
    @SneakyThrows
    void whenSendMailReceiveNotificationEmailValidButAnErrorOccursWhenSendingEmailThenThrowMailException() {
        when(this.templateEngine.process(anyString(), any(Context.class))).thenReturn("html");
        when(this.emailSender.createMimeMessage()).thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        doThrow(RuntimeException.class).when(this.emailSender).send(any(MimeMessage.class));

        this.service.sendEmail(this.notificationEmail);

        verify(this.templateEngine, times(1)).process(anyString(), this.contextCaptor.capture());
        verify(this.emailSender, times(1)).createMimeMessage();
        verify(this.emailSender, times(1)).send(this.mimeMessageCaptor.capture());
        verify(this.sendEmailAdapter, times(1)).sendNotificationEmail(any(NotificationEmail.class));

        assertEquals("email@email.com", this.mimeMessageCaptor.getValue().getAllRecipients()[0].toString());
        assertEquals("Nome", this.contextCaptor.getValue().getVariable("consumerName"));
        assertEquals(8.0, this.contextCaptor.getValue().getVariable("total"));
        assertEquals(1, ((List<Item>) this.contextCaptor.getValue().getVariable("itens")).size());
        assertEquals("banco", this.contextCaptor.getValue().getVariable("bank"));
        assertEquals("link", this.contextCaptor.getValue().getVariable("link"));

    }

    @Test
    @SneakyThrows
    void whenSendMailReceiveNotificationEmailValidButAnErrorOccursWhenSendingEmailThenThrowMessagingException() {
        this.notificationEmail.setEmail(null);
        when(this.templateEngine.process(anyString(), any(Context.class))).thenReturn("html");
        when(this.emailSender.createMimeMessage()).thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        doNothing().when(this.emailSender).send(any(MimeMessage.class));

        this.service.sendEmail(this.notificationEmail);

        verify(this.templateEngine, times(1)).process(anyString(), this.contextCaptor.capture());
        verify(this.emailSender, times(1)).createMimeMessage();
        verify(this.emailSender, never()).send(any(MimeMessage.class));
        verify(this.sendEmailAdapter, times(1)).sendNotificationEmail(any(NotificationEmail.class));

        assertEquals("Nome", this.contextCaptor.getValue().getVariable("consumerName"));
        assertEquals(8.0, this.contextCaptor.getValue().getVariable("total"));
        assertEquals(1, ((List<Item>) this.contextCaptor.getValue().getVariable("itens")).size());
        assertEquals("banco", this.contextCaptor.getValue().getVariable("bank"));
        assertEquals("link", this.contextCaptor.getValue().getVariable("link"));

    }

}
