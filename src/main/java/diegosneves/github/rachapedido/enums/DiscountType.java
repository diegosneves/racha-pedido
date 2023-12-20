package diegosneves.github.rachapedido.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa o tipo de desconto.
 */
public enum DiscountType {

    @JsonProperty(value = "cash")
    CASH(1.0),
    @JsonProperty(value = "percentage")
    PERCENTAGE(100.0);

    private final Double calculation;

    DiscountType(Double calculation) {
        this.calculation = calculation;
    }

    public Double discountAmount(Double value) {
        return value / this.calculation;
    }

}
