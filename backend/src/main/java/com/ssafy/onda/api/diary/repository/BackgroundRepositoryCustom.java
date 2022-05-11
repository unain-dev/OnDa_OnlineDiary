package com.ssafy.onda.api.diary.repository;

import com.ssafy.onda.api.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface BackgroundRepositoryCustom {

    List<LocalDate> findByMemberAndDiaryDateLike(Member member, LocalDate diaryDate);

}
