package diegosneves.github.rachapedido.exceptions;

/**
 * A classe {@link NullBuyerException} representa uma exceção lançada quando um objeto comprador é nulo.
 */
public class NullBuyerException extends RuntimeException {

    public static final ErroHandler ERROR = ErroHandler.NULL_BUYER;

    /**
     * Cria uma nova instância da classe {@link NullBuyerException} com a mensagem de erro e a causa especificadas.
     *
     * @param message A mensagem de erro.
     */
    public NullBuyerException(String message) {
        super(ERROR.errorMessage(message));
    }
}
