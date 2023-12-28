package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.adapter.SendEmailAdapter;
import diegosneves.github.rachapedido.model.NotificationEmail;
import diegosneves.github.rachapedido.service.contract.EmailServiceContract;
import diegosneves.github.rachapedido.utils.ThymeLeafContextUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * Classe de serviço de Email que implementa a interface {@link EmailServiceContract}.
 * Esta classe é responsável por manipular ações relacionadas ao envio de email.
 *
 * @author diegosneves
 */
@Service
@Slf4j
public class EmailService implements EmailServiceContract {

    private static final String EMAIL_TEMPLATE = "email";
    private final SendEmailAdapter sendEmailAdapter;

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;


    @Autowired
    public EmailService(SendEmailAdapter sendEmailAdapter, JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.sendEmailAdapter = sendEmailAdapter;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendPaymentEmail(NotificationEmail notificationEmail) {
        this.sendEmailAdapter.sendNotificationEmail(notificationEmail);
    }

    @Override
    public void sendEmail(NotificationEmail notificationEmail) {

        try {
            MimeMessage message = this.emailSender.createMimeMessage();
            Context context = ThymeLeafContextUtil.generateBy(notificationEmail);
            String html = templateEngine.process(EMAIL_TEMPLATE, context);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(notificationEmail.getEmail());
            helper.setSubject(String.format("%s - %s", "Racha Pedido", notificationEmail.getBank()));
            helper.setText(html, true);

            this.emailSender.send(message);

        } catch (RuntimeException | MessagingException e) {
            log.error("Error sending email", e);
            this.sendPaymentEmail(notificationEmail);
        }
    }

}
