package diegosneves.github.rachapedido.dto;

/**
 * Esta classe representa um Data Transfer Object (DTO) para exceções.
 * Contém a mensagem de exceção e o código de status.
 */
public record ExceptionDTO(String message, int statusCode) {
}
