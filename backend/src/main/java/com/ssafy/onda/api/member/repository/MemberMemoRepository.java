package com.ssafy.onda.api.member.repository;

import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.api.member.entity.MemberMemo;
import com.ssafy.onda.global.common.entity.MemoType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberMemoRepository extends JpaRepository<MemberMemo, Long>, MemberMemoRepositoryCustom {

    List<MemberMemo> findAllByBackground(Background background);

    List<MemberMemo> findAllByMemoTypeAndMemoSeqIn(MemoType memoType, List<Long> memoSeqs);

}
