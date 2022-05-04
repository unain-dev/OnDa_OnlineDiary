package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "isChecked", "content" })
@NoArgsConstructor
@Getter
public class ChecklistItemDto {

    private Boolean isChecked;

    private String content;

    @Builder
    public ChecklistItemDto(Boolean isChecked, String content) {
        this.isChecked = isChecked;
        this.content = content;
    }
}
