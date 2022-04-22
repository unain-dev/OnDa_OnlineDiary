package com.ssafy.onda.global.error.exception;

import com.ssafy.onda.global.error.dto.ErrorStatus;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorStatus errorStatus;

    public BusinessException(String msg, ErrorStatus errorStatus) {
        super(msg);
        this.errorStatus = errorStatus;
    }

    public BusinessException(ErrorStatus errorStatus) {
        super(errorStatus.getMsg());
        this.errorStatus = errorStatus;
    }
}
