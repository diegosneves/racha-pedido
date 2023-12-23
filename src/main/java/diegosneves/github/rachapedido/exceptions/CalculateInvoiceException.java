package diegosneves.github.rachapedido.exceptions;

/**
 * A classe {@link CalculateInvoiceException} representa uma exceção que é acionada quando ocorre um erro
 * durante o processo de fechamento da fatura. Esta classe é uma extensão da classe {@link RuntimeException}.
 *
 * @author diegosneves
 * @see RuntimeException
 */
public class CalculateInvoiceException extends RuntimeException {

    public static final ErroHandler ERROR = ErroHandler.INVOICE_FAILED;

    /**
     * Constrói uma nova instância de {@link CalculateInvoiceException} com a mensagem descritiva específica.
     *
     * @param message A mensagem detalhada. Deve fornecer informações complementares sobre a causa da exceção.
     */
    public CalculateInvoiceException(String message) {
        super(ERROR.errorMessage(message));
    }

    /**
     * Cria uma nova instância de {@link CalculateInvoiceException} com a mensagem de detalhe especificada e a causa.
     *
     * @param message a mensagem de detalhes, fornecendo informações adicionais sobre a causa da exceção
     * @param e       a causa da exceção
     */
    public CalculateInvoiceException(String message, Throwable e) {
        super(ERROR.errorMessage(message), e);
    }

}
