package com.ssafy.onda.api.diary.repository;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.api.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepositoryCustom {

    List<LocalDate> findByMemberAndDiaryDateLike(Member member, LocalDate diaryDate);

    List<Long> findTextSeqsByDiaryAndKeyword(Diary diary, String keyword);

    List<Long> findAccountBookSeqsByDiaryAndKeyword(Diary diary, String keyword);

    List<Long> findChecklistSeqsByDiaryAndKeyword(Diary diary, String keyword);

}
