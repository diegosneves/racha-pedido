package diegosneves.github.rachapedido.adapter;

import diegosneves.github.rachapedido.exceptions.ErroHandler;
import diegosneves.github.rachapedido.model.NotificationEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * A classe {@link SendEmailAdapter} é responsável por enviar e-mails de notificação usando a API REST.
 * Ela estende a classe {@link HttpAdapter}, que fornece uma implementação comum para fazer requisições HTTP.
 * A classe é anotada com {@link Component @Component}, tornando-a elegível para detecção automática e configuração como bean pelo Spring Framework.
 * <p>
 * Ela contém um construtor que injeta a URL para enviar e-mails usando a anotação {@link Value}.
 * <p>
 * A classe fornece um método {@link SendEmailAdapter#sendNotificationEmail(NotificationEmail) sendNotificationEmail(NotificationEmail)} para enviar o email de notificação.
 * O método utiliza o objeto {@link RestTemplateSimpleWebClient restTemplateSimpleWebClient} para enviar uma requisição POST para a URL especificada com o {@link NotificationEmail notificationEmail} como corpo da requisição.
 * A resposta da requisição é registrada usando o objeto {@code log}.
 * Se ocorrer uma exceção durante a requisição, ela é capturada e registrada como erro usando o objeto {@code log}.
 * <p>
 * Exemplo de uso:
 * <pre> {@code
 * SendEmailAdapter sendEmailAdapter = new SendEmailAdapter(url); // Inicializa com a URL
 * sendEmailAdapter.sendNotificationEmail(notificationEmail); // Envia o email de notificação
 * }</pre>
 * <p>
 * Observação: Substitua {@code url} e {@code notificationEmail} com os valores adequados de acordo com o seu caso de uso.
 *
 * @autor diegosneves
 * @see HttpAdapter
 * @see NotificationEmail
 * @see ErroHandler
 */
@Component
@Slf4j
public class SendEmailAdapter extends HttpAdapter {

    private final String url;

    @Autowired
    public SendEmailAdapter(@Value("${spring.api.url.send-email}") String url) {
        this.url = url;
    }

    /**
     * Envia um e-mail de notificação usando a API REST.
     *
     * @param notificationEmail O objeto de email de notificação contendo o {@link NotificationEmail conteúdo} do email.
     */
    public void sendNotificationEmail(NotificationEmail notificationEmail) {

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
