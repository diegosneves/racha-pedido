package diegosneves.github.rachapedido.exceptions;

/**
 * A classe {@link ConstructorDefaultUndefinedException} é uma exceção que será lançada quando uma classe não possuir um construtor padrão.
 *
 * @see RuntimeException
 * @author diegosneves
 */
public class ConstructorDefaultUndefinedException extends RuntimeException {

    public static final ErroHandler ERROR = ErroHandler.CONSTRUCTOR_DEFAULT_UNDEFINED;

    /**
     * Cria uma nova instância da classe {@link ConstructorDefaultUndefinedException} com a mensagem e causa fornecidas.
     *
     * @param message A mensagem com as informações sobre o motivo do erro.
     * @param e A causa do erro
     */
    public ConstructorDefaultUndefinedException(String message, Throwable e) {
        super(ERROR.errorMessage(message), e);
    }
}
