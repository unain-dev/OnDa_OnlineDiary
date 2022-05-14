package com.ssafy.onda.api.filter.service;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.api.filter.dto.response.MonthFilterDto;

import java.util.List;

public interface FilterService {

    List<MonthFilterDto> search(Long type, CustomUserDetails details);

    List<MonthFilterDto> searchBox(Long type, String keyword, CustomUserDetails details);

    Object preview(CustomUserDetails details, int memoTypeSeq, String memoSeqList);

}
