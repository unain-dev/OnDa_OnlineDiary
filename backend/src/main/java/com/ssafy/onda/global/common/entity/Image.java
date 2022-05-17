package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import com.ssafy.onda.global.common.entity.embedded.Memo;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"imageSeq"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_image")
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long imageSeq;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "originName", column = @Column(name = "origin_name", nullable = false)),
            @AttributeOverride(name = "encodedName", column = @Column(name = "encoded_name", nullable = false)),
            @AttributeOverride(name = "savedPath", column = @Column(name = "saved_path", nullable = false))
    })
    private FileInfo fileInfo;

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
    public Image(Long imageSeq, FileInfo fileInfo, Memo memo, Diary diary) {
        this.imageSeq = imageSeq;
        this.fileInfo = fileInfo;
        this.memo = memo;
        this.diary = diary;
    }

    public void changeMemoEntity(Long x, Long y, Long width, Long height) {
        this.memo = Memo.builder()
                .x(x)
                .y(y)
                .width(width)
                .height(height)
                .build();
    }
}
