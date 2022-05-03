package com.ssafy.onda.global.common.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"memoTypeSeq", "memoType"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_memo_type")
@Entity
public class MemoType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memoTypeSeq;

    @Column(nullable = false, unique = true)
    private String memoType;

    @Builder
    public MemoType(Long memoTypeSeq, String memoType) {
        this.memoTypeSeq = memoTypeSeq;
        this.memoType = memoType;
    }
}
