package com.ssafy.onda.api.member.repository;

import java.util.List;

public interface MemberMemoRepositoryCustom {

    List<Long> findAllMemberSeqsByMemoTypeSeqAndMemoSeqs(int memoTypeSeq, List<Long> memoSeqs);

}
