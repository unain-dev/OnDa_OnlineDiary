package com.ssafy.onda.api.filter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString(of = { "memoTypeSeq", "count", "memoSeqList"})
@NoArgsConstructor
@Getter
public class MonthMemoListDto {

    private Long memoTypeSeq;

    private int count;

    private List<Long> memoSeqList;

    @Builder
    public MonthMemoListDto(Long memoTypeSeq, int count, List<Long> memoSeqList) {
        this.memoTypeSeq = memoTypeSeq;
        this.count = count;
        this.memoSeqList = memoSeqList;
    }
}
