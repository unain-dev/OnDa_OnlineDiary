package com.ssafy.onda.global.error.exception;

import com.ssafy.onda.global.error.dto.ErrorStatus;

public class CustomException extends BusinessException {

    public CustomException(String value, ErrorStatus errorStatus) {
        super(value, errorStatus);
    }
}
