package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotificationEmail {

    private String email;
    private String consumerName;
    private Double total;
    private List<Item> itens;
    private String link;

}
