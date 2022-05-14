package com.ssafy.onda.global.common.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"memoTypeSeq", "memoTypeName"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_memo_type")
@Entity
public class MemoType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memoTypeSeq;

    @Column(nullable = false, unique = true)
    private String memoTypeName;

    @QueryProjection
    @Builder
    public MemoType(Long memoTypeSeq, String memoTypeName) {
        this.memoTypeSeq = memoTypeSeq;
        this.memoTypeName = memoTypeName;
    }
}
