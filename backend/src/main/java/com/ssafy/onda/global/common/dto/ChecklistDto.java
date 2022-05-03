package com.ssafy.onda.global.common.dto;

import lombok.*;

import java.util.List;

@ToString(of = { "checklistHeader", "checklistItems" })
@NoArgsConstructor
@Getter
public class ChecklistDto {

    private String checklistHeader;

    private List<ChecklistItemDto> checklistItems;

    @Builder
    public ChecklistDto(String checklistHeader, List<ChecklistItemDto> checklistItems) {
        this.checklistHeader = checklistHeader;
        this.checklistItems = checklistItems;
    }
}
