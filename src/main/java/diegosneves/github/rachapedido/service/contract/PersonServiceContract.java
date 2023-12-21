package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.exceptions.NullBuyerException;
import diegosneves.github.rachapedido.model.Person;

import java.util.List;

public interface PersonServiceContract {

    /**
     * Recupera a lista de consumidores com base no comprador e nos consumidores fornecidos.
     *
     * @param buyer As informações do {@link PersonDTO comprador}.
     * @param consumers A lista de {@link PersonDTO consumidores}.
     * @return A lista de {@link Person consumidores}.
     * @throws NullBuyerException Se o comprador for nulo.
     */
    List<Person> getConsumers(PersonDTO buyer, List<PersonDTO> consumers) throws NullBuyerException;

}
