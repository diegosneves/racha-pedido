package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
