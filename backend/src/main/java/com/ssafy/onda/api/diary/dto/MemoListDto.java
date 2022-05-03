package com.ssafy.onda.api.diary.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(of = { "id", "width", "height", "x", "y", "memoTypeSeq" })
@NoArgsConstructor
@Getter
public class MemoListDto {

    private Long id;

    private Long width;

    private Long height;

    private Long x;

    private Long y;

    private Integer memoTypeSeq;

    private Object info;

    @Builder
    public MemoListDto(Long id, Long width, Long height, Long x, Long y, Integer memoTypeSeq, Object info) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.memoTypeSeq = memoTypeSeq;
        this.info = info;
    }
}
