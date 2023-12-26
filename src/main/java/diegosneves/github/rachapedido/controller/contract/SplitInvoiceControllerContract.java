package diegosneves.github.rachapedido.controller.contract;

import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * A interface {@link SplitInvoiceControllerContract} representa o contrato para lidar com solicitações HTTP relacionadas à divisão de faturas.
 */
public interface SplitInvoiceControllerContract {

    @PostMapping(value = "/invoice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Receber os dados para realizar a divisão da fatura", tags = "Racha Pedido", parameters = {
            @Parameter(name = "descontos", description = "Tipos de descontos a serem aplicados no campo `discountType` do corpo da requisição",
                    schema = @Schema(enumAsRef = true, defaultValue = "cash", allowableValues = {"cash", "percentage", "no discount"})),
            @Parameter(name = "bancos", description = "Tipos de bancos aceitos que devem ser preenchidos no campo `selectedBank` do corpo da requisição",
                    schema = @Schema(enumAsRef = true, defaultValue = "nubank", allowableValues = {"nubank", "picpay"}))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Divisão da Fatura realizada com sucesso", content = @Content)
    })
    ResponseEntity<SplitInvoiceResponse> splitInvoice(@RequestBody SplitInvoiceRequest request);

}
