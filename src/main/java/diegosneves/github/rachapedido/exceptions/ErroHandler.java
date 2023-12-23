package diegosneves.github.rachapedido.exceptions;

import org.springframework.http.HttpStatus;

/**
 * A enum {@link ErroHandler} representa diferentes manipuladores de erros,
 * buscando padronizar as mensagens de erro.
 *
 * @author diegosneves
 */
public enum ErroHandler {

    CONSTRUCTOR_DEFAULT_UNDEFINED("Classe [ %s ] deve declarar um construtor padrão.", HttpStatus.NOT_IMPLEMENTED),
    NULL_BUYER("O Objeto [ %s ] não deve ser nulo.", HttpStatus.BAD_REQUEST),
    ORDER_FAILED("Houve um erro no fechamento do pedido: [ %s ].", HttpStatus.BAD_REQUEST), // TODO - Buscar deixar os demais nessa forma mais generica
    INVOICE_FAILED("Ocorreu uma falha durante a execução do cálculo da fatura: [ %s ].", HttpStatus.BAD_REQUEST), // TODO - Buscar deixar os demais nessa forma mais generica
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
     * Retorna o código de status HTTP associado ao erro.
     *
     * @return O código numérico do status HTTP relacionado com o erro.
     */
    public int getStatusCodeValue() {
        return this.httpStatus.value();
    }

    /**
     * Obtém o status HTTP associado ao erro.
     *
     * @return O código de status HTTP relacionado ao erro.
     */
    public HttpStatus getHttpStatusCode() {
        return this.httpStatus;
    }
}
