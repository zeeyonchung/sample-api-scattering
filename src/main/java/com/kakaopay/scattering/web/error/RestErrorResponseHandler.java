package com.kakaopay.scattering.web.error;

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
        log.error("ServletRequestBindingException", e);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = ErrorResponse.of(e, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

}
