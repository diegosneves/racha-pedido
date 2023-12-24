package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.model.NotificationEmail;

public interface EmailServiceContract {

    void sendPaymentEmail(NotificationEmail notificationEmail);

}
