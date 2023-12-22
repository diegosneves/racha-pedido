package diegosneves.github.rachapedido.config;

import diegosneves.github.rachapedido.dto.ExceptionDTO;
import diegosneves.github.rachapedido.exceptions.CloseOrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A classe {@link ControllerExceptionHandler} é um manipulador de exceções global para controladores.
 * Ela lida com as exceções lançadas durante o processamento de solicitações e gera respostas de erro apropriadas.
 * A classe é anotada com {@link RestControllerAdvice} para aplicar o tratamento de exceção globalmente
 * a todas as classes de controlador.
 *
 * @autor diegosneves
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> generalError(Exception exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

    @ExceptionHandler(CloseOrderException.class)
    public ResponseEntity<ExceptionDTO> orderRelatedFaileures(CloseOrderException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), CloseOrderException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(CloseOrderException.ERROR.getHttpStatusCode()).body(dto);
    }

}
