package com.ssafy.onda.api.diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.onda.api.member.entity.Member;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.ssafy.onda.api.diary.entity.QDiary.*;

public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LocalDate> findByMemberAndDiaryDateLike(Member member, LocalDate diaryDate) {
        return queryFactory
                .select(diary.diaryDate)
                .from(diary)
                .where(diary.diaryDate.year().eq(diaryDate.getYear())
                        .and(diary.diaryDate.month().eq(diaryDate.getMonthValue()))
                        .and(diary.member.eq(member)))
                .fetch();
    }
}
