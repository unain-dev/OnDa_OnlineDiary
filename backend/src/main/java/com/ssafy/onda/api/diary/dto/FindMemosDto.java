package com.ssafy.onda.api.diary.dto;

import com.ssafy.onda.global.common.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FindMemosDto {

    private List<Text> texts;

    private List<AccountBook> accountBooks;

    private List<Checklist> checklists;

    private List<AccountBookItem> accountBookItems;

    private List<ChecklistItem> checklistItems;

    @Builder
    public FindMemosDto(List<Text> texts, List<AccountBook> accountBooks, List<Checklist> checklists, List<AccountBookItem> accountBookItems, List<ChecklistItem> checklistItems) {
        this.texts = texts;
        this.accountBooks = accountBooks;
        this.checklists = checklists;
        this.accountBookItems = accountBookItems;
        this.checklistItems = checklistItems;
    }
}
