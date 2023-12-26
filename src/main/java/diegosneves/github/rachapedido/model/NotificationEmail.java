package diegosneves.github.rachapedido.model;

import lombok.*;

import java.util.List;

/**
 * Classe modelo para Notificação de Email.
 * Esta classe contém informações necessárias para compor um email de notificação.
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NotificationEmail {

    private String email;
    private String consumerName;
    private Double total;
    private List<Item> itens;
    private String bank;
    private String link;

}
