package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "header", "content" })
@NoArgsConstructor
@Getter
public class TextDto {

    private String header;

    private String content;

    @Builder
    public TextDto(String header, String content) {
        this.header = header;
        this.content = content;
    }
}
