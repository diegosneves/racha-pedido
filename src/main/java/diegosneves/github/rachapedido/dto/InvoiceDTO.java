package diegosneves.github.rachapedido.dto;

import lombok.*;

/**
 * A classe {@link InvoiceDTO} representa um objeto de transferência de dados de fatura (DTO).
 * Ela contém informações sobre o nome do consumidor, valor consumido, valor total a pagar e a porcentagem do total da fatura consumida.
 *
 * <p>Este objeto possui os seguintes campos:
 * <ul>
 *   <li><b>consumerName</b>: Um {@link String} que representa o nome do consumidor.</li>
 *   <li><b>valueConsumed</b>: Um {@link Double} que representa o valor consumido pelo consumidor.</li>
 *   <li><b>totalPayable</b>: Um {@link Double} que representa o valor total a pagar na fatura.</li>
 *   <li><b>percentageConsumedTotalBill</b>: Um {@link Double} que representa a porcentagem do total da fatura consumida.</li>
 * </ul>
 * </p>
 * Exemplo de Uso:
 * <pre>{@code
 *     InvoiceDTO dto = new InvoiceDTO();
 *     dto.setConsumerName("João da Silva");
 *     dto.setValueConsumed(100.0);
 *     dto.setTotalPayable(90.0);
 *     dto.setPercentageConsumedTotalBill(90.0);
 * }</pre>
 *
 * @author diegosneves
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceDTO {

    private String consumerName;
    private Double valueConsumed;
    private Double totalPayable;
    private Double percentageConsumedTotalBill;

}
