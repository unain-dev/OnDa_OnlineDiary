package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "isChecked", "checklistItemText" })
@NoArgsConstructor
@Getter
public class ChecklistItemDto {

    private Boolean isChecked;

    private String checklistItemText;

    @Builder
    public ChecklistItemDto(Boolean isChecked, String checklistItemText) {
        this.isChecked = isChecked;
        this.checklistItemText = checklistItemText;
    }
}
