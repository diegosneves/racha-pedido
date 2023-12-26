package diegosneves.github.rachapedido.core;

import diegosneves.github.rachapedido.enums.DiscountType;
import diegosneves.github.rachapedido.model.Invoice;
import diegosneves.github.rachapedido.model.Person;

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
    public abstract Invoice calculateDiscount(Person person, Double discountAmount, DiscountType type, Double total, Double deliveryFee);

    protected Invoice checkNext(Person person, Double discountAmount, DiscountType type, Double total, Double deliveryFee) {
        if (this.next == null) {
            return new Invoice();
        }
        return next.calculateDiscount(person, discountAmount, type, total, deliveryFee);
    }

}
