package diegosneves.github.rachapedido.mapper;

import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Person;

public class InvoiceFromPersonMapper implements BuildingStrategy<Invoice, Person> {

    @Override
    public Invoice run(Person origem) {
        return Invoice.builder()
                .consumerName(origem.getPersonName())
                .email(origem.getEmail())
                .isBuyer(origem.getIsBuyer())
                .items(origem.getItems())
                .build();
    }
}
