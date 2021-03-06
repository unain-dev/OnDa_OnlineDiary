package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.global.common.entity.embedded.Memo;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = { "textSeq", "header", "content", "memo" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_text")
@Entity
public class Text {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long textSeq;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "y", nullable = false)),
            @AttributeOverride(name = "width", column = @Column(name = "width", nullable = false)),
            @AttributeOverride(name = "height", column = @Column(name = "height", nullable = false))
    })
    private Memo memo;

    @JoinColumn(name = "diary_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Diary diary;

    @Builder
    public Text(Long textSeq, String header, String content, Memo memo, Diary diary) {
        this.textSeq = textSeq;
        this.header = header;
        this.content = content;
        this.memo = memo;
        this.diary = diary;
    }
}
