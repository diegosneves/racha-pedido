package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.exceptions.PersonConstraintsException;
import diegosneves.github.rachapedido.model.Person;

import java.util.List;

/**
 * A interface {@link PersonServiceContract} estabelece o contrato para a classe PersonService.
 * Ela provê um método para recuperar uma lista de {@link Person consumidores}, baseando-se nos dados do {@link PersonDTO comprador} e nos {@link PersonDTO participantes da divisão} fornecidos.
 *
 * @author diegosneves
 */
public interface PersonServiceContract {

    /**
     * Recupera a lista de consumidores com base no {@link PersonDTO comprador} e nos {@link PersonDTO consumidores} fornecidos.
     *
     * @param buyer As informações do {@link PersonDTO comprador}.
     * @param consumers A lista de {@link PersonDTO consumidores}.
     * @return A lista com todos os {@link Person consumidores}.
     * @throws PersonConstraintsException Se o comprador for nulo.
     */
    List<Person> getConsumers(PersonDTO buyer, List<PersonDTO> consumers) throws PersonConstraintsException;

}
