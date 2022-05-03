package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.MemoType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoTypeRepository extends JpaRepository<MemoType, Long> {

    MemoType findByMemoTypeSeq(Long memoTypeSeq);

}
