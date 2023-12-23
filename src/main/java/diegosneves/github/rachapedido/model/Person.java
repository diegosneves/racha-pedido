package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A classe {@link Person} é responsável por representar a abstração de uma pessoa.
 * Cada instância dessa classe contém infomações como o nome e o email da pessoa.
 * Além disso, possui um atributo do tipo {@link Boolean} que indica se a pessoa é uma compradora.
 * Por fim, ela contém também uma lista de {@link Item} que representa os itens associados à pessoa.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Person {

    private String personName;
    private String email;
    private Boolean isBuyer = Boolean.FALSE;
    private List<Item> items = new ArrayList<>();

}
