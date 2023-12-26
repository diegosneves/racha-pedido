package diegosneves.github.rachapedido.mapper;

import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Person;

/**
 * Classe responsável por mapear um objeto da classe {@link Person} para um objeto da classe {@link Invoice}.
 * Este mapeamento ocorre de acordo com a estrutura definida no método {@code run}.
 * Implementa a estratégia de construção definida na interface {@link BuildingStrategy}.<br>
 *
 * @author diegosneves
 */
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
