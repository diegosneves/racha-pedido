package diegosneves.github.rachapedido.mapper;

import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.NotificationEmail;

/**
 * Essa classe é responsável por mapear um objeto do tipo {@link Invoice} para um objeto {@link NotificationEmail}.<br>
 *
 * @author diegosneves
 */
public class NotificationEmailMapper implements BuildingStrategy <NotificationEmail, Invoice> {

    @Override
    public NotificationEmail mapper(Invoice origem) {
        return NotificationEmail.builder()
                .consumerName(origem.getConsumerName())
                .email(origem.getEmail())
                .itens(origem.getItems())
                .total(origem.getTotalPayable())
                .build();
    }

}
