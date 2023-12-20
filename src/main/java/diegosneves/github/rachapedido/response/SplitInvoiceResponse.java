package diegosneves.github.rachapedido.response;

import diegosneves.github.rachapedido.dto.PersonDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SplitInvoiceResponse {
    private PersonDTO buyer; // TODO - Definir os campos de retorno
}
