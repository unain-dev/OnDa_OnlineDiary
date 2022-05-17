package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.api.filter.dto.MonthMemoListDto;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.api.filter.dto.response.MonthFilterDto;

import java.util.List;

public interface FilterService {

    List<MonthFilterDto> search(int memoTypeSeq, String keyword, CustomUserDetails details);

    MonthMemoListDto getMonthMemoText(Diary diary, String keyword);

    MonthMemoListDto getMonthMemoAccountBook(Diary diary, String keyword);

    MonthMemoListDto getMonthMemoChecklist(Diary diary, String keyword);

    Object preview(CustomUserDetails details, int memoTypeSeq, String memoSeqList);

}
