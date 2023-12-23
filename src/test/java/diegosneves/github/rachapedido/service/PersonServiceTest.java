package diegosneves.github.rachapedido.service;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.exceptions.NullBuyerException;
import diegosneves.github.rachapedido.model.Item;
import diegosneves.github.rachapedido.model.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    private PersonDTO personI;
    private PersonDTO personII;
    private Item itemI;
    private Item itemII;
    private Item itemIII;

    @BeforeEach
    void setUp() {
        this.itemI = Item.builder()
                .name("Item I")
                .price(40.0)
                .build();

        this.itemII = Item.builder()
                .name("Item II")
                .price(2.0)
                .build();

        this.itemIII = Item.builder()
                .name("Item III")
                .price(8.0)
                .build();

        this.personI = PersonDTO.builder()
                .personName("Buyer - Person I")
                .email("buyer@teste.com")
                .items(List.of(this.itemI, this.itemII))
                .build();

        this.personII = PersonDTO.builder()
                .personName("Consumer - Person II")
                .email("consumer@teste.com")
                .items(List.of(this.itemIII))
                .build();
    }

    @Test
    @SneakyThrows
    void whenConvertBuyerToPersonReceivePersonDTOThenPersonBuyerMustBeReturn() {
        String personNameExpect = "Buyer - Person I";
        String emailExpect = "buyer@teste.com";


        Method method = this.service.getClass().getDeclaredMethod("convertBuyerToPerson", PersonDTO.class);
        method.setAccessible(true);

        Person actual = (Person) method.invoke(this.service, this.personI);

        assertNotNull(actual);
        assertEquals(personNameExpect, actual.getPersonName());
        assertEquals(emailExpect, actual.getEmail());
        assertEquals(2, actual.getItems().size());
        assertEquals(this.itemI, actual.getItems().get(0));
        assertEquals(this.itemII, actual.getItems().get(1));
        assertTrue(actual.getIsBuyer());

    }

    @Test
    @SneakyThrows
    void whenConvertToPersonReceivePersonDTOThenPersonConsumerMustBeReturn() {
        String personNameExpect = "Consumer - Person II";
        String emailExpect = "consumer@teste.com";


        Method method = this.service.getClass().getDeclaredMethod("convertToPerson", PersonDTO.class);
        method.setAccessible(true);

        Person actual = (Person) method.invoke(this.service, this.personII);

        assertNotNull(actual);
        assertEquals(personNameExpect, actual.getPersonName());
        assertEquals(emailExpect, actual.getEmail());
        assertEquals(1, actual.getItems().size());
        assertEquals(this.itemIII, actual.getItems().get(0));
        assertFalse(actual.getIsBuyer());

    }

    @Test
    @SneakyThrows
    void whenConvertAllToPersonReceivePersonDTOListThenPersonListMustBeReturn() {
        List<PersonDTO> personsToBeInvoked = new ArrayList<>();
        personsToBeInvoked.add(this.personI);
        personsToBeInvoked.add(this.personII);

        Method method = this.service.getClass().getDeclaredMethod("convertAllConsumersToPerson", List.class);
        method.setAccessible(true);

        List<Person> actual = (List<Person>) method.invoke(this.service, personsToBeInvoked);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertFalse(actual.get(0).getIsBuyer());
        assertFalse(actual.get(1).getIsBuyer());
    }

    @Test
    void whenGetConsumersReceivePersonDTOBuyerAndConsumersListThenPersonListMustBeReturn() {
        List<Person> consumers = service.getConsumers(personI, List.of(personII));

        assertEquals(2, consumers.size());
        assertEquals("Buyer - Person I", consumers.get(0).getPersonName());
        assertEquals("Consumer - Person II", consumers.get(1).getPersonName());
        assertEquals("buyer@teste.com", consumers.get(0).getEmail());
        assertEquals("consumer@teste.com", consumers.get(1).getEmail());
        assertEquals(2, consumers.get(0).getItems().size());
        assertEquals(1, consumers.get(1).getItems().size());
        assertEquals(itemI, consumers.get(0).getItems().get(0));
        assertEquals(itemII, consumers.get(0).getItems().get(1));
        assertEquals(itemIII, consumers.get(1).getItems().get(0));
        assertTrue(consumers.get(0).getIsBuyer());
        assertFalse(consumers.get(1).getIsBuyer());
    }

    @Test
    void whenGetConsumersReceivePersonDTOBuyerAndConsumersListNullThenPersonListMustBeReturnWithAConsumer() {
        List<Person> consumers = service.getConsumers(personI, null);

        assertEquals(1, consumers.size());
        assertEquals("Buyer - Person I", consumers.get(0).getPersonName());
        assertEquals("buyer@teste.com", consumers.get(0).getEmail());
        assertEquals(2, consumers.get(0).getItems().size());
        assertEquals(itemI, consumers.get(0).getItems().get(0));
        assertEquals(itemII, consumers.get(0).getItems().get(1));
        assertTrue(consumers.get(0).getIsBuyer());
    }

    @Test
    void whenGetConsumersReceivePersonDTOBuyerNullThenThrowNullBuyerException() {
        Exception exception = assertThrows(NullBuyerException.class, () -> service.getConsumers(null, List.of(personII)));

        assertInstanceOf(NullBuyerException.class, exception);
        assertEquals(NullBuyerException.ERROR.errorMessage(PersonDTO.class.getSimpleName()), exception.getMessage());
    }

}
