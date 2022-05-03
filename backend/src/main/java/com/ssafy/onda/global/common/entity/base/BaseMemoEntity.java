package com.ssafy.onda.global.common.entity.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseMemoEntity {

    @Column(nullable = false)
    private Long x;

    @Column(nullable = false)
    private Long y;

    @Column(nullable = false)
    private Long width;

    @Column(nullable = false)
    private Long height;

    public BaseMemoEntity(Long x, Long y, Long width, Long height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
