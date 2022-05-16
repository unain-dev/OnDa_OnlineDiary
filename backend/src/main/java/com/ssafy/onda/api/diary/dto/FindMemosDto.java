package com.ssafy.onda.api.diary.dto;

import com.ssafy.onda.global.common.dto.AccountBookItemDto;
import com.ssafy.onda.global.common.dto.ChecklistItemDto;
import com.ssafy.onda.global.common.entity.*;
import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class FindMemosDto {

    private List<Text> texts;

    private List<AccountBook> accountBooks;

    private List<Checklist> checklists;

    private List<Image> images;

    private List<Sticker> stickers;

    private List<AccountBookItem> accountBookItems;

    private List<ChecklistItem> checklistItems;

    Map<AccountBook, List<AccountBookItemDto>> accountBookMap;

    Map<Checklist, List<ChecklistItemDto>> checklistMap;

    Set<FileInfo> savedFile;

    @Builder
    public FindMemosDto(List<Text> texts, List<AccountBook> accountBooks, List<Checklist> checklists, List<Image> images, List<Sticker> stickers, List<AccountBookItem> accountBookItems, List<ChecklistItem> checklistItems, Map<AccountBook, List<AccountBookItemDto>> accountBookMap, Map<Checklist, List<ChecklistItemDto>> checklistMap, Set<FileInfo> savedFile) {
        this.texts = texts;
        this.accountBooks = accountBooks;
        this.checklists = checklists;
        this.images = images;
        this.stickers = stickers;
        this.accountBookItems = accountBookItems;
        this.checklistItems = checklistItems;
        this.accountBookMap = accountBookMap;
        this.checklistMap = checklistMap;
        this.savedFile = savedFile;
    }
}
