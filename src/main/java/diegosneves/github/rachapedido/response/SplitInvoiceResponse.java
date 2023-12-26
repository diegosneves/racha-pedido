package diegosneves.github.rachapedido.response;

import diegosneves.github.rachapedido.dto.InvoiceDTO;
import lombok.*;

import java.util.List;

/**
 * A Classe {@link SplitInvoiceResponse} é responsável por armazenar a resposta da divisão das faturas.
 * Essa classe contém uma lista de faturas e o valor total a ser pago.
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SplitInvoiceResponse {

    private List<InvoiceDTO> invoices;
    private Double totalPayable;
}
