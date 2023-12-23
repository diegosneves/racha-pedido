package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.dto.InvoiceDTO;
import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.Invoice;

public abstract class DiscountStrategy {

    protected DiscountStrategy next;

    public static DiscountStrategy link(DiscountStrategy first, DiscountStrategy... chain) {
        DiscountStrategy current = first;
        for (DiscountStrategy nextLink : chain) {
            current.next = nextLink;
            current = nextLink;
        }
        return first;
    }
    public abstract Invoice calculateDiscount(InvoiceDTO dto, Double discountAmount, DiscountType type, Double total, Double deliveryFee);

    protected Invoice checkNext(InvoiceDTO dto, Double discountAmount, DiscountType type, Double total, Double deliveryFee) {
        if (this.next == null) {
            return new Invoice();
        }
        return next.calculateDiscount(dto, discountAmount, type, total, deliveryFee);
    }

}
