package diegosneves.github.rachapedido.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa os diferentes tipos de descontos que podem ser aplicados.
 *
 * @author diegosneves
 */
public enum DiscountType {

    @JsonProperty(value = "cash")
    CASH(1.0),
    @JsonProperty(value = "percentage")
    PERCENTAGE(100.0),
    @JsonProperty(value = "no discount")
    NO_DISCOUNT(0.0);

    private final Double calculation;

    DiscountType(Double calculation) {
        this.calculation = calculation;
    }

    public Double discountAmount(Double value) {
        return this.calculation == 0.0 ? 0.0 : value / this.calculation;
    }

}
