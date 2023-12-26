package diegosneves.github.rachapedido.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa os diferentes tipos de descontos que podem ser aplicados a uma transação/pedido.
 * Cada tipo de desconto tem um valor associado único que serve para calcular o valor do desconto.<br><br>
 *
 * <b>Tipos de Desconto:</b>
 * <ul>
 *  <li><b>CASH</b>: representa um desconto que é aplicado diretamente no valor total.</li>
 *  <li><b>PERCENTAGE</b>: representa um desconto em percentual aplicado sobre o valor total.</li>
 *  <li><b>NO_DISCOUNT</b>: representa uma transação sem desconto aplicado.</li>
 * </ul>
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

    /**
     * Calcula o valor do desconto com base no valor de entrada e no tipo de desconto.
     *
     * @param value - valor sobre o qual o desconto deve ser calculado.
     * @return valor do desconto. Se o tipo de desconto for {@link DiscountType#NO_DISCOUNT NO_DISCOUNT}, retorna 0.0.
     */
    public Double discountAmount(Double value) {
        return this.calculation == 0.0 ? this.calculation : value / this.calculation;
    }

}
