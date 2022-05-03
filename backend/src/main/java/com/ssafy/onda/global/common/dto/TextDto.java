package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "textHeader", "textContent" })
@NoArgsConstructor
@Getter
public class TextDto {

    private String textHeader;

    private String textContent;

    @Builder
    public TextDto(String textHeader, String textContent) {
        this.textHeader = textHeader;
        this.textContent = textContent;
    }
}
