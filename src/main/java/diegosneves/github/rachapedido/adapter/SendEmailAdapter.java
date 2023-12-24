package diegosneves.github.rachapedido.adapter;

import diegosneves.github.rachapedido.core.BankAccount;
import diegosneves.github.rachapedido.exceptions.ErroHandler;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.NotificationEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@Slf4j
public class SendEmailAdapter extends HttpAdapter {

    private final String url;

    @Autowired
    public SendEmailAdapter(@Value("${spring.api.url.send-email}") String url) {
        this.url = url;
    }

    public void sendNotificationEmail(NotificationEmail notificationEmail) {
//        Item itemII = Item.builder()
//                .name("Item II")
//                .price(2.00)
//                .build();
//
//        Item itemIII = Item.builder()
//                .name("Item III")
//                .price(8.00)
//                .build();
//        notificationEmail = NotificationEmail.builder()
//                .email("neves.diegoalex@outlook.com")
//                .consumerName("Diego Neves")
//                .total(10.00)
//                .itens(List.of(itemII, itemIII))
//                .link(BankAccount.PICPAY.paymentLink())
//                .build();

        try {
            String response = this.restTemplateSimpleWebClient
                    .getRestTemplate()
                    .postForEntity(this.url, notificationEmail, String.class)
                    .getBody();
            log.info(response);
        } catch (RestClientException e) {
            log.error(ErroHandler.EMAIL_SEND_FAILURE.errorMessage(notificationEmail.getEmail()), e);
        }

    }

}
