package diegosneves.github.rachapedido.dto;

import diegosneves.github.rachapedido.model.Item;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
