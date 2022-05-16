package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.MemoType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoTypeRepository extends JpaRepository<MemoType, Long> {

    MemoType findByMemoTypeSeq(Long memoTypeSeq);

    List<MemoType> findAllByMemoTypeSeq(Long type);

    List<MemoType> findAllByMemoTypeSeqIn(List<Long> listTemp);
}
