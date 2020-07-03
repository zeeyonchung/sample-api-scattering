package com.kakaopay.scattering.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private static final String DEFAULT_MESSAGE = "에러가 발생하였습니다.";

    private int status;
    private String message;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(Exception e, HttpStatus httpStatus) {
        return new ErrorResponse(httpStatus.value(), e.getLocalizedMessage());
    }

    public static ErrorResponse of(HttpStatus httpStatus) {
        return new ErrorResponse(httpStatus.value(), DEFAULT_MESSAGE);
    }
}
