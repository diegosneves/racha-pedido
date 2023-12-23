package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.mapper.BuilderMapper;
import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.utils.RoundUtil;

public class NoDiscountStrategy extends DiscountStrategy {

    @Override
    public Invoice calculateDiscount(InvoiceDTO dto, Double discountAmount, DiscountType type, Double total, Double deliveryFee) {
        if (DiscountType.NO_DISCOUNT.name().equals(type.name())) {
            dto.setPercentageConsumedTotalBill(dto.getValueConsumed() / total);
            dto.setTotalPayable(RoundUtil.round((total - type.discountAmount(discountAmount) + deliveryFee) * dto.getPercentageConsumedTotalBill()));
            return BuilderMapper.builderMapper(Invoice.class, dto);
        }
        return this.checkNext(dto, discountAmount, type, total, deliveryFee);
    }
}
