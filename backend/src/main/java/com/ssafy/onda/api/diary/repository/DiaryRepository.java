package com.ssafy.onda.api.diary.repository;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

    Optional<Diary> findByMemberAndDiaryDate(Member member, LocalDate parse);

    List<Diary> findByMemberOrderByDiarySeqAsc(Member member);

}
