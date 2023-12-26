package diegosneves.github.rachapedido.mapper;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.model.Person;

/**
 * A classe {@link BuyerPersonMapper} implementa a interface {@link BuildingStrategy} e é responsável por criar
 * um novo objeto {@link Person} baseado no objeto {@link PersonDTO} fornecido usando o construtor
 * padrão.<br>
 *
 * @author diegosneves
 */
public class BuyerPersonMapper implements BuildingStrategy<Person, PersonDTO> {

    /**
     * Executa o padrão de construtor para criar um novo objeto {@link Person} com base no {@link PersonDTO} fornecido.
     *
     * @param origem O objeto {@link PersonDTO} contendo os dados a serem usados no objeto {@link Person}.
     * @return Um novo objeto {@link Person} com os campos definidos com base nos dados do {@link PersonDTO}.
     * O campo {@link Boolean isBuyer} é sempre definido como {@link Boolean#TRUE verdadeiro}.
     */
    @Override
    public Person run(PersonDTO origem) {
        return Person.builder()
                .personName(origem.getPersonName())
                .isBuyer(Boolean.TRUE)
                .items(origem.getItems())
                .email(origem.getEmail())
                .build();
    }
}
