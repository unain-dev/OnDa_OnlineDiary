package com.ssafy.onda.api.filter.dto.response;

import com.ssafy.onda.api.filter.dto.MonthMemoListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString(of = { "DiaryDate", "monthMemoListDto"})
@NoArgsConstructor
@Getter
public class MonthFilterDto {

    private String DiaryDate;

    private List<MonthMemoListDto> monthMemoListDto;

    @Builder
    public MonthFilterDto(String diaryDate, List<MonthMemoListDto> monthMemoListDto) {
        DiaryDate = diaryDate;
        this.monthMemoListDto = monthMemoListDto;
    }
}
