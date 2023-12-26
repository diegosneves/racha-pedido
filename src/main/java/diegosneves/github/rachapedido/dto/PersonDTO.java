package diegosneves.github.rachapedido.dto;

import diegosneves.github.rachapedido.model.Item;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe {@link PersonDTO} é uma versão Data Transfer Object (DTO) de uma pessoa, contendo campos para {@link String nome}, {@link String e-mail} e uma lista de {@link Item itens}.
 *
 * Esta classe é usada principalmente para transferência de dados entre processos ou componentes e ajuda a evitar múltiplas chamadas ao projeto atual.
 *
 * @see diegosneves.github.rachapedido.model.Person Person
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonDTO {

    private String personName;
    private String email;
    private List<Item> items = new ArrayList<>();

}
