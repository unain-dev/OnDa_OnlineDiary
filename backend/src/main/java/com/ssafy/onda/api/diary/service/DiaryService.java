package com.ssafy.onda.api.diary.service;

import com.ssafy.onda.api.diary.dto.FindMemosDto;
import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.dto.response.ResDiaryDto;
import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.api.member.entity.MemberMemo;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiaryService {

    void save(CustomUserDetails details, ReqDiaryDto reqDiaryDto, List<MultipartFile> multipartFiles);

    void deleteByMemberAndDiaryDate(CustomUserDetails details, String diaryDate);

    void delete(Background background);

    void checkDateValidation(String date);

    void saveMemoType();

    ResDiaryDto load(CustomUserDetails details, String diaryDate);

    FindMemosDto find(List<MemberMemo> memberMemos);

    List<Integer> getDays(CustomUserDetails details, String diaryDate);

}
