package com.ssafy.onda.api.diary.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.api.member.entity.Member;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.ssafy.onda.api.diary.entity.QDiary.*;
import static com.ssafy.onda.global.common.entity.QAccountBook.*;
import static com.ssafy.onda.global.common.entity.QAccountBookItem.*;
import static com.ssafy.onda.global.common.entity.QChecklist.*;
import static com.ssafy.onda.global.common.entity.QChecklistItem.*;
import static com.ssafy.onda.global.common.entity.QText.*;

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

    @Override
    public List<Long> findTextSeqsByDiaryAndKeyword(Diary diary, String keyword) {

        JPAQuery<Long> query = queryFactory
                .select(text.textSeq)
                .from(text)
                .where(text.diary.eq(diary));

        if (keyword != null) {
            query.where(text.header.contains(keyword)
                    .or(text.content.contains(keyword)));
        }

        return query.fetch();
    }

    @Override
    public List<Long> findAccountBookSeqsByDiaryAndKeyword(Diary diary, String keyword) {

        JPAQuery<Long> query = queryFactory
                .selectDistinct(accountBook.accountBookSeq)
                .from(accountBook, accountBookItem)
                .where(accountBook.diary.eq(diary)
                        .and(accountBook.eq(accountBookItem.accountBook)));

        if (keyword != null) {
            query.where(accountBookItem.content.contains(keyword));
        }

        return query.fetch();
    }

    @Override
    public List<Long> findChecklistSeqsByDiaryAndKeyword(Diary diary, String keyword) {

        JPAQuery<Long> query = queryFactory
                .selectDistinct(checklist.checklistSeq)
                .from(checklist, checklistItem)
                .where(checklist.diary.eq(diary)
                        .and(checklist.eq(checklistItem.checklist)));

        if (keyword != null) {
            query.where(checklist.checklistHeader.contains(keyword)
                    .or(checklistItem.content.contains(keyword)));
        }

        return query.fetch();
    }
}
