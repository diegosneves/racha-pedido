package diegosneves.github.rachapedido.exceptions;

import org.springframework.http.HttpStatus;

/**
 * O {@link  ErroHandler} é um enum que representa diferentes manipuladores de erros. Com o objetivo de padrozinar as mensagens de erro.
 */
public enum ErroHandler {

    CONSTRUCTOR_DEFAULT_UNDEFINED("Classe [ %s ] deve declarar um construtor padrão.", HttpStatus.NOT_IMPLEMENTED),
    NULL_BUYER("O Objeto [ %s ] não deve ser nulo.", HttpStatus.BAD_REQUEST),
    CLASS_MAPPING_FAILURE("Ocorreu um erro ao tentar mapear a classe [ %s ].", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    ErroHandler(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Formata uma mensagem de erro com um valor de dados fornecido.
     *
     * @param data O valor dos dados a ser incluído na mensagem de erro.
     * @return A mensagem de erro formatada.
     */
    public String errorMessage(String data) {
        return String.format(this.message, data);
    }

    /**
     * Retorna o código de status da resposta HTTP.
     *
     * @return O código de status da resposta HTTP.
     */
    public int getStatusCode() {
        return this.httpStatus.value();
    }

}
