package com.ssafy.onda.global.common.entity.base;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@ToString(of = { "x", "y", "width", "height" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Memo {

    @Column(nullable = false)
    private Long x;

    @Column(nullable = false)
    private Long y;

    @Column(nullable = false)
    private Long width;

    @Column(nullable = false)
    private Long height;

    @Builder
    public Memo(Long x, Long y, Long width, Long height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}


