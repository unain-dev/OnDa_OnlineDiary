package com.ssafy.onda.global.common.dto.base;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class BaseResponseDto {

    private Integer status;

    private String msg;

    private Map<String, Object> data;

    @Builder
    public BaseResponseDto(Integer status, String msg, Map<String, Object> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
