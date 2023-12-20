package diegosneves.github.rachapedido.request;

import diegosneves.github.rachapedido.dto.PersonDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import lombok.*;

import java.util.List;

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
