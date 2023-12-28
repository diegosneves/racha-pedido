package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.model.NotificationEmail;

/**
 * Interface de serviço de email do sistema.
 *
 * @author diegosneves
 */
public interface EmailServiceContract {

    /**
     * Envia um email de notificação de pagamento.
     *
     * @param notificationEmail O email de {@link NotificationEmail notificação} a ser enviado. Este contem informações sobre o pagamento realizado.
     */
    void sendPaymentEmail(NotificationEmail notificationEmail);

    /**
     * Envia um e-mail usando os dados fornecidos por {@link NotificationEmail}.
     *
     * @param data os dados de {@link NotificationEmail} usados para compor o e-mail.
     */
    void sendEmail(NotificationEmail data);

}
