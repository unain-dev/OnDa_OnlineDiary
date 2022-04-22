package com.ssafy.onda.global.error.dto;

import lombok.Getter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
public class ErrorResponseDto {

    private Integer status;

    private String msg;

    private ErrorResponseDto(final ErrorStatus errorStatus) {
        this.msg = errorStatus.getMsg();
        this.status = errorStatus.getBackToFrontStatus();
    }

    private ErrorResponseDto(final ErrorStatus errorStatus, String msg) {
        this.msg = msg;
        this.status = errorStatus.getBackToFrontStatus();
    }

    public static ErrorResponseDto of(final ErrorStatus errorStatus) {
        return new ErrorResponseDto(errorStatus);
    }

    public static ErrorResponseDto of(final ErrorStatus errorStatus, String msg) {
        return new ErrorResponseDto(errorStatus, msg);
    }

    public static ErrorResponseDto of(MethodArgumentTypeMismatchException e) {
//        final String value = e.getValue() == null ? "" : e.getValue().toString();
        return new ErrorResponseDto(ErrorStatus.INVALID_TYPE_VALUE);
    }
}
