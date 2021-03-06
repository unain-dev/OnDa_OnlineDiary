package com.ssafy.onda.global.error.exception;

import com.ssafy.onda.global.error.dto.ErrorStatus;

public class CustomException extends BusinessException {

    public CustomException(String value, ErrorStatus errorStatus) {
        super(value, errorStatus);
    }

    public CustomException(String reason, String path, ErrorStatus errorStatus) {
        super("value : " + reason + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t [ path ] " + path, errorStatus);
    }

    public CustomException(String memberId, String reason, String path, ErrorStatus errorStatus) {
        super("member : " + memberId + ", caused by " + reason + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t [ path ] " + path, errorStatus);
    }
}
