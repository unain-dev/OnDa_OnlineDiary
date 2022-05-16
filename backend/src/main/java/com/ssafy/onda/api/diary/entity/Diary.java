package com.ssafy.onda.api.diary.entity;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.onda.api.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString(of = {"diarySeq", "diaryDate"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_diary",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_between_member_seq_and_diary_date",
                        columnNames = {"member_seq", "diaryDate"}
                )
        }
)
@Entity
public class Diary {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long diarySeq;

    @Column(nullable = false)
    private LocalDate diaryDate;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @QueryProjection
    @Builder
    public Diary(Long diarySeq, LocalDate diaryDate, Member member) {
        this.diarySeq = diarySeq;
        this.diaryDate = diaryDate;
        this.member = member;
    }
}
