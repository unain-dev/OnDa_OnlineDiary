package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import com.ssafy.onda.global.common.entity.embedded.Memo;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"videoSeq"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_video")
@Entity
public class Video {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long videoSeq;

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
    public Video(Long videoSeq, FileInfo fileInfo, Memo memo, Diary diary) {
        this.videoSeq = videoSeq;
        this.fileInfo = fileInfo;
        this.memo = memo;
        this.diary = diary;
    }
}
