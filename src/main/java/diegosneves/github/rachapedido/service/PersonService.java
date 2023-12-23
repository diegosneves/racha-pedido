package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.exceptions.NullBuyerException;
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

    private static final String BUYER_ERROR = PersonDTO.class.getSimpleName();

    @Override
    public List<Person> getConsumers(PersonDTO buyer, List<PersonDTO> consumers) throws NullBuyerException {
        if (isNull(buyer)) {
            throw new NullBuyerException(BUYER_ERROR);
        }
        List<Person> personList = new ArrayList<>();
        personList.add(this.convertBuyerToPerson(buyer));
        if (nonNull(consumers)) {
            personList.addAll(this.convertAllConsumersToPerson(consumers));
        }

        return personList;
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
        return BuilderMapper.builderMapper(Person.class, consumer);
    }

    /**
     * Converte um objeto do tipo {@link PersonDTO} em um objeto do tipo {@link Person}.
     *
     * @param buyer O objeto {@link PersonDTO} que representa um consumidor.
     * @return O objeto {@link Person} que representa um comprador, resultado da conversão.
     */
    private Person convertBuyerToPerson(PersonDTO buyer) {
        BuyerPersonMapper mapper = new BuyerPersonMapper();
        return BuilderMapper.builderMapper(Person.class, buyer, mapper);
    }
}
