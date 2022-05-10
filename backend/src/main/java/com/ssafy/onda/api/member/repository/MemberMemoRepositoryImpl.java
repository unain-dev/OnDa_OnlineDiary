package com.ssafy.onda.api.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.onda.api.diary.entity.QBackground.*;
import static com.ssafy.onda.api.member.entity.QMember.*;
import static com.ssafy.onda.api.member.entity.QMemberMemo.*;
import static com.ssafy.onda.global.common.entity.QMemoType.*;

public class MemberMemoRepositoryImpl implements MemberMemoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberMemoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findAllMemberSeqsByMemoTypeSeqAndMemoSeqs(int memoTypeSeq, List<Long> memoSeqs) {

        BooleanExpression or = memberMemo.memoSeq.eq(memoSeqs.get(0));

        for (Long memoSeq : memoSeqs) {
            or = or.or(memberMemo.memoSeq.eq(memoSeq));
        }

        JPAQuery<Long> query = queryFactory
                .select(member.memberSeq)
                .from(member, background, memberMemo, memoType)
                .where(background.member.eq(member)
                        .and(background.eq(memberMemo.background))
                        .and(memberMemo.memoType.eq(memoType))
                        .and(memberMemo.memoType.memoTypeSeq.eq((long) memoTypeSeq))
                        .and(or)
                );

        return query.fetch();
    }
}
