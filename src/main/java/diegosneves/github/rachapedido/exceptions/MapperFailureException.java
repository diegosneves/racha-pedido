package diegosneves.github.rachapedido.exceptions;

/**
 * O {@link MapperFailureException} representa uma exceção que é lançada quando ocorre um erro durante o mapeamento de classes.
 *
 * @see RuntimeException
 * @author diegosneves
 */
public class MapperFailureException extends RuntimeException {

    public static final ErroHandler ERROR = ErroHandler.CLASS_MAPPING_FAILURE;

    /**
     * Cria uma nova instância da classe {@link MapperFailureException} com a mensagem de erro e a causa especificadas.
     *
     * @param message A mensagem de erro.
     * @param e A causa do erro.
     */
    public MapperFailureException(String message, Throwable e) {
        super(ERROR.errorMessage(message), e);
    }
}
