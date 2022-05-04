package com.ssafy.onda.api.diary.dto.response;

import com.ssafy.onda.api.diary.dto.MemoListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString(of = { "diaryDate", "totalCnt" })
@Getter
public class ResDiaryDto {

    private String diaryDate;

    private Integer totalCnt;

    private List<MemoListDto> memoList;

    @Builder
    public ResDiaryDto(String diaryDate, Integer totalCnt, List<MemoListDto> memoList) {
        this.diaryDate = diaryDate;
        this.totalCnt = totalCnt;
        this.memoList = memoList;
    }
}
