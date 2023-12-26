package diegosneves.github.rachapedido.model;

import diegosneves.github.rachapedido.dto.InvoiceDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um modelo de divisão de fatura.
 *
 * <p>Esta classe é usada principalmente para manter um registro da divisão de contas aplicada a várias faturas.
 * Ela mantém uma lista de objetos {@link InvoiceDTO} que representam faturas individuais e um valor total a ser pago.
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BillSplit {

    private List<InvoiceDTO> invoices = new ArrayList<>();
    private Double totalPayable;

}
