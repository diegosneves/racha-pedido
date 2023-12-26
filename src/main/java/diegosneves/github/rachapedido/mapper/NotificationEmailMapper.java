package diegosneves.github.rachapedido.mapper;

import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.NotificationEmail;
import diegosneves.github.rachapedido.model.Person;

public class NotificationEmailMapper implements BuildingStrategy <NotificationEmail, Invoice> {

    @Override
    public NotificationEmail run(Invoice origem) {
        return NotificationEmail.builder()
                .consumerName(origem.getConsumerName())
                .email(origem.getEmail())
                .itens(origem.getItems())
                .total(origem.getTotalPayable())
                .build();
    }

}
