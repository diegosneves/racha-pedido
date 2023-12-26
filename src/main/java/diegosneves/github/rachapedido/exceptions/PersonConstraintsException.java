package diegosneves.github.rachapedido.exceptions;

/**
 * A classe {@link PersonConstraintsException} representa uma exceção lançada quando um erro ocorre durante a validação de uma {@link diegosneves.github.rachapedido.model.Person pessoa}.
 *
 * @see RuntimeException
 * @author diegosneves
 */
public class PersonConstraintsException extends RuntimeException {

    public static final ErroHandler ERROR = ErroHandler.PERSON_CONSTRAINTS;

    /**
     * Cria uma nova instância da classe {@link PersonConstraintsException} com a mensagem de erro e a causa especificadas.
     *
     * @param message A mensagem de erro.
     */
    public PersonConstraintsException(String message) {
        super(ERROR.errorMessage(message));
    }
}
