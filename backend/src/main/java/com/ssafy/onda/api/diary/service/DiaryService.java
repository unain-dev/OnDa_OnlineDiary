package com.ssafy.onda.api.diary.service;

import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.global.common.auth.CustomUserDetails;

public interface DiaryService {

    void save(CustomUserDetails details, ReqDiaryDto reqDiaryDto);

    void deleteByMemberAndDiaryDate(CustomUserDetails details, String diaryDate);

    void delete(Background background);

    void saveMemoType();
}
