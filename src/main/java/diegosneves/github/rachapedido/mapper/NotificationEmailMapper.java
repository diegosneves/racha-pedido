package diegosneves.github.rachapedido.mapper;

import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.NotificationEmail;
import diegosneves.github.rachapedido.model.Person;

public class NotificationEmailMapper implements BuildingStrategy <NotificationEmail, Person> {

    @Override
    public NotificationEmail run(Person origem) {
        return NotificationEmail.builder()
                .consumerName(origem.getPersonName())
                .email(origem.getEmail())
                .itens(origem.getItems())
                .total(origem.getItems().stream().mapToDouble(Item::getPrice).sum())
                .build();
    }

}
