package com.ssafy.onda.api.diary.repository;

import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BackgroundRepository extends JpaRepository<Background, Long> {

    Optional<Background> findByMemberAndDiaryDate(Member member, LocalDate diaryDate);

    List<Background> findByMember(Member member);
}
