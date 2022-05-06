package com.ssafy.onda.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = { "emoji" })
@NoArgsConstructor
@Getter
public class StickerDto {

    private String emoji;

    @Builder
    public StickerDto(String emoji) {
        this.emoji = emoji;
    }
}
