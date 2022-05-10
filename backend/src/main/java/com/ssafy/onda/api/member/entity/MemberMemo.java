package com.ssafy.onda.api.member.entity;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.global.common.entity.MemoType;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"memberMemoSeq", "memoSeq"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_member_memo",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_between_memo_seq_and_memo_type",
                        columnNames={"memoSeq", "memo_type_seq"}
                )
}
)
@Entity
public class MemberMemo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memberMemoSeq;

    @Column(nullable = false)
    private Long memoSeq;

    @JoinColumn(name = "memo_type_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MemoType memoType;

    @JoinColumn(name = "background_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Background background;

    @QueryProjection
    @Builder
    public MemberMemo(Long memberMemoSeq, Long memoSeq, MemoType memoType, Background background) {
        this.memberMemoSeq = memberMemoSeq;
        this.memoSeq = memoSeq;
        this.memoType = memoType;
        this.background = background;
    }
}
