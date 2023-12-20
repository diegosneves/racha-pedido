package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Person {

    private String name;
    private String email;
    private Boolean isBuyer;
    private List<Item> items;

}
