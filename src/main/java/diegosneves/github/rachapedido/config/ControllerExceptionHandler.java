package diegosneves.github.rachapedido.config;

import diegosneves.github.rachapedido.dto.ExceptionDTO;
import diegosneves.github.rachapedido.exceptions.*;
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
 * @author diegosneves
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Manipula exceções gerais e retorna uma resposta de erro apropriada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> generalError(Exception exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

    /**
     * Este método lida com {@link CalculateInvoiceException} e retorna uma resposta de erro apropriada.
     *
     * @param exception A {@link CalculateInvoiceException} que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e o código de status HTTP.
     */
    @ExceptionHandler(CalculateInvoiceException.class)
    public ResponseEntity<ExceptionDTO> orderRelatedFaileures(CalculateInvoiceException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), CalculateInvoiceException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(CalculateInvoiceException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Este método mapeia a exceção {@link ConstructorDefaultUndefinedException} para um objeto {@link ResponseEntity<ExceptionDTO>}.
     * Cria um novo objeto {@link ExceptionDTO} com a mensagem da exceção e o código de status correspondente.
     *
     * @param exception A exceção {@link ConstructorDefaultUndefinedException} que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e o código de status HTTP.
     */
    @ExceptionHandler(ConstructorDefaultUndefinedException.class)
    public ResponseEntity<ExceptionDTO> mapperRelatedFaileures(ConstructorDefaultUndefinedException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), ConstructorDefaultUndefinedException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(ConstructorDefaultUndefinedException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Mapeia a {@link MapperFailureException} para uma {@link ResponseEntity<ExceptionDTO>}.
     * Cria um novo objeto {@link ExceptionDTO} com a mensagem da exceção e o código de status correspondente.
     *
     * @param exception A {@link MapperFailureException} que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e o código de status HTTP correspondente.
     */
    @ExceptionHandler(MapperFailureException.class)
    public ResponseEntity<ExceptionDTO> mapperRelatedFaileures(MapperFailureException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), MapperFailureException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(MapperFailureException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Trata as exceções do tipo {@link PersonConstraintsException} e retorna uma resposta de erro adequada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} que contém um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP.
     */
    @ExceptionHandler(PersonConstraintsException.class)
    public ResponseEntity<ExceptionDTO> consumerRelatedFaileures(PersonConstraintsException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), PersonConstraintsException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(PersonConstraintsException.ERROR.getHttpStatusCode()).body(dto);
    }

}
