package diegosneves.github.rachapedido.service.contract;

import diegosneves.github.rachapedido.request.SplitInvoiceRequest;
import diegosneves.github.rachapedido.response.SplitInvoiceResponse;

/**
 * Interface de Contrato do Serviço de Divisão de Fatura.
 * <p>
 * Esta interface fornece a definição do método para a operação de divisão de fatura.
 *
 * @author diegosneves
 */
public interface SplitInvoiceServiceContract {

    /**
     * Realiza a divisão de uma fatura.
     *
     * @param request O objeto que encapsula os dados necessários para a operação de divisão de fatura.
     * @return O resultado da operação de divisão de fatura representado por um objeto de resposta {@link SplitInvoiceResponse}.
     */
    SplitInvoiceResponse splitInvoice(SplitInvoiceRequest request);

}
