package com.ssafy.onda.api.diary.entity;

import com.ssafy.onda.api.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@ToString(of = {"backgroundSeq", "diaryDate"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_background",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_between_member_seq_and_diary_date",
                        columnNames = {"member_seq", "diaryDate"}
                )
        }
)
@Entity
public class Background {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long backgroundSeq;

    @Column(nullable = false)
    private LocalDate diaryDate;

    @JoinColumn(name = "member_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Background(Long backgroundSeq, LocalDate diaryDate, Member member) {
        this.backgroundSeq = backgroundSeq;
        this.diaryDate = diaryDate;
        this.member = member;
    }
}
