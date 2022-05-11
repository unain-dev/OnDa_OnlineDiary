package com.ssafy.onda.api.diary.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.onda.api.diary.entity.QBackground;
import com.ssafy.onda.api.member.entity.Member;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.ssafy.onda.api.diary.entity.QBackground.*;

public class BackgroundRepositoryImpl implements BackgroundRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BackgroundRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LocalDate> findByMemberAndDiaryDateLike(Member member, LocalDate diaryDate) {

        return queryFactory
                .select(background.diaryDate)
                .from(background)
                .where(background.diaryDate.year().eq(diaryDate.getYear())
                        .and(background.diaryDate.month().eq(diaryDate.getMonthValue()))
                        .and(background.member.eq(member)))
                .fetch();
    }
}
