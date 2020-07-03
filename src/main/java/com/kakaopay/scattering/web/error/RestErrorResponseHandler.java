package com.kakaopay.scattering.web.error;

import com.kakaopay.scattering.domain.exception.MoneyAssignException;
import com.kakaopay.scattering.domain.exception.MoneyDivideException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestErrorResponseHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error(e.getClass().getSimpleName(), e);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(httpStatus);

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    protected ResponseEntity<ErrorResponse> handleServletRequestBindingException(ServletRequestBindingException e) {
        return getErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return getErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MoneyAssignException.class)
    protected ResponseEntity<ErrorResponse> handleMoneyAssignException(MoneyAssignException e) {
        return getErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MoneyDivideException.class)
    protected ResponseEntity<ErrorResponse> handleMoneyDivideException(MoneyDivideException e) {
        return getErrorResponseEntity(e, HttpStatus.NOT_IMPLEMENTED);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception e, HttpStatus httpStatus) {
        log.error(e.getClass().getSimpleName(), e);
        ErrorResponse response = ErrorResponse.of(e, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }
}
