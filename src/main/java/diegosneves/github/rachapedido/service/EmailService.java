package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.adapter.SendEmailAdapter;
import diegosneves.github.rachapedido.model.NotificationEmail;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço de Email que implementa a interface {@link EmailServiceContract}.
 * Esta classe é responsável por manipular ações relacionadas ao envio de email.
 *
 * @author diegosneves
 */
@Service
public class EmailService implements EmailServiceContract {

    private final SendEmailAdapter sendEmailAdapter;

    @Autowired
    public EmailService(SendEmailAdapter sendEmailAdapter) {
        this.sendEmailAdapter = sendEmailAdapter;
    }

    @Override
    public void sendPaymentEmail(NotificationEmail notificationEmail) {
        this.sendEmailAdapter.sendNotificationEmail(notificationEmail);
    }

}
