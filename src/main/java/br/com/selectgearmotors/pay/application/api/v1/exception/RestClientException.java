package br.com.selectgearmotors.pay.application.api.v1.exception;

import br.com.selectgearmotors.pay.commons.exception.MercadoPagoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestClientException extends ResponseEntityExceptionHandler {

   // @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Map<String, String> body = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach((error) -> {
            body.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MercadoPagoException.class)
    public ResponseEntity<Object> handleMercadoPagoException(MercadoPagoException exception) {
        Map<String, String> body = new HashMap<>();
        body.put("error_message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
