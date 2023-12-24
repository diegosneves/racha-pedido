package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.exceptions.PersonConstraintsException;
import diegosneves.github.rachapedido.mapper.BuilderMapper;
import diegosneves.github.rachapedido.mapper.BuyerPersonMapper;
import diegosneves.github.rachapedido.model.Person;
import diegosneves.github.rachapedido.service.contract.PersonServiceContract;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * A classe {@link PersonService} implementa a interface {@link PersonServiceContract} e fornece métodos
 * para interagir com objetos do tipo Person.
 *
 * @author diegosneves
 */
@Service
public class PersonService implements PersonServiceContract {

    private static final String BUYER_ERROR = "É necessário fornecer informações válidas para o comprador.";
    private static final String MISSING_NAME_ERROR_MESSAGE = "O nome é um campo obrigatório. Por favor, insira um nome.";
    private static final String EMAIL_MISSING_ERROR_MESSAGE = "O email é um campo obrigatório. Por favor, insira um email.";

    @Override
    public List<Person> getConsumers(PersonDTO buyer, List<PersonDTO> consumers) throws PersonConstraintsException {
        if (isNull(buyer)) {
            throw new PersonConstraintsException(BUYER_ERROR);
        }
        List<Person> personList = new ArrayList<>();
        personList.add(this.convertBuyerToPerson(buyer));
        if (nonNull(consumers)) {
            personList.addAll(this.convertAllConsumersToPerson(consumers));
        }

        return personList;
    }

    /**
     * Valida os dados de uma {@link PersonDTO pessoa}.
     *
     * @param personDTO Os dados da pessoa a serem validados.
     * @return Os dados validados da pessoa.
     * @throws PersonConstraintsException se o nome ou e-mail da pessoa estiverem faltando.
     */
    private PersonDTO validatePersonData(PersonDTO personDTO) throws PersonConstraintsException {
        if (isNull(personDTO.getPersonName()) || personDTO.getPersonName().isBlank()) {
            throw new PersonConstraintsException(MISSING_NAME_ERROR_MESSAGE);
        } else if (isNull(personDTO.getEmail()) || personDTO.getEmail().isBlank()) {
            throw new PersonConstraintsException(EMAIL_MISSING_ERROR_MESSAGE);
        } else {
            return personDTO;
        }
    }

    /**
     * Converte uma lista de objetos {@link PersonDTO} em uma lista de objetos {@link Person}.
     *
     * @param consumer A lista de objetos {@link PersonDTO} a serem convertidos.
     * @return A lista de objetos {@link Person} convertidos.
     */
    private List<Person> convertAllConsumersToPerson(List<PersonDTO> consumer) {
        return consumer.stream().map(this::convertToPerson).toList();
    }

    /**
     * Converte um objeto {@link PersonDTO} em um objeto {@link Person}.
     *
     * @param consumer O objeto {@link PersonDTO} a ser convertido.
     * @return O objeto {@link Person} convertido.
     */
    private Person convertToPerson(PersonDTO consumer) {
        return BuilderMapper.builderMapper(Person.class, this.validatePersonData(consumer));
    }

    /**
     * Converte um objeto do tipo {@link PersonDTO} em um objeto do tipo {@link Person}.
     *
     * @param buyer O objeto {@link PersonDTO} que representa um consumidor.
     * @return O objeto {@link Person} que representa um comprador, resultado da conversão.
     */
    private Person convertBuyerToPerson(PersonDTO buyer) {
        BuyerPersonMapper mapper = new BuyerPersonMapper();
        return BuilderMapper.builderMapper(Person.class, this.validatePersonData(buyer), mapper);
    }
}
