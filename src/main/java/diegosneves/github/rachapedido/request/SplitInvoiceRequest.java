package diegosneves.github.rachapedido.request;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import lombok.*;

import java.util.List;

/**
 * A classe {@link SplitInvoiceRequest} representa uma requisição para dividir um valor de fatura.
 * <p>
 * Esta classe contém informações sobre:
 * <ol>
 * <li>{@link PersonDTO comprador} - O indivíduo que fez a compra.</li>
 * <li>{@link PersonDTO compradores} - Uma lista de pessoas com quem o comprador pretende dividir a fatura.</li>
 * <li>{@link DiscountType discountType;} - O tipo de desconto aplicado (se houver).</li>
 * <li>{@link Double discount;} - O valor do desconto (se houver).</li>
 * <li>{@link Double deliveryFee;} - A taxa de entrega (se aplicável).</li>
 * </ol>
 * Cada instância desta classe representa uma requisição única para dividir uma fatura.
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SplitInvoiceRequest {
    private PersonDTO buyer;
    private List<PersonDTO> splitInvoiceWith;
    private DiscountType discountType;
    private Double discount;
    private Double deliveryFee;
}
