package com.kakaopay.scattering.web.error;

import com.kakaopay.scattering.domain.exception.MoneyAssignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestErrorResponseHandler {

    @ExceptionHandler(ServletRequestBindingException.class)
    protected ResponseEntity<ErrorResponse> handleServletRequestBindingException(ServletRequestBindingException e) {
        return getErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MoneyAssignException.class)
    protected ResponseEntity<ErrorResponse> handleMoneyAssignException(MoneyAssignException e) {
        return getErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception e, HttpStatus httpStatus) {
        log.error(e.getClass().getSimpleName(), e);
        ErrorResponse response = ErrorResponse.of(e, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }
}
