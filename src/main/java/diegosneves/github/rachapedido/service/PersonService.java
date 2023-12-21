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

    private List<Person> convertAllConsumersToPerson(List<PersonDTO> consumer) {
        return consumer.stream().map(this::convertToPerson).toList();
    }

    private Person convertToPerson(PersonDTO consumer) {
        return BuilderMapper.builderMapper(Person.class, consumer);
    }

    private Person convertBuyerToPerson(PersonDTO buyer) {
        BuyerPersonMapper mapper = new BuyerPersonMapper();
        return BuilderMapper.builderMapper(Person.class, buyer, mapper);
    }
}
