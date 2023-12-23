package diegosneves.github.rachapedido.exceptions;

/**
 * A classe {@link CloseOrderException} representa uma exceção que é acionada quando ocorre um erro
 * durante o processo de fechamento de um pedido. Esta classe é uma extensão da classe {@link RuntimeException}.
 *
 * @author diegosneves
 * @see RuntimeException
 */
public class CloseOrderException extends RuntimeException {

    public static final ErroHandler ERROR = ErroHandler.ORDER_FAILED;

    /**
     * Constrói uma nova instância de {@link CloseOrderException} com a mensagem descritiva específica.
     *
     * @param message A mensagem detalhada. Deve fornecer informações complementares sobre a causa da exceção.
     */
    public CloseOrderException(String message) {
        super(ERROR.errorMessage(message));
    }

    /**
     * Cria uma nova instância de {@link CloseOrderException} com a mensagem de detalhe especificada e a causa.
     *
     * @param message a mensagem de detalhes, fornecendo informações adicionais sobre a causa da exceção
     * @param e       a causa da exceção
     */
    public CloseOrderException(String message, Throwable e) {
        super(ERROR.errorMessage(message), e);
    }

}
