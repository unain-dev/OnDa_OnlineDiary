package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"videoSeq"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_video")
@Entity
public class Video extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long videoSeq;

    @Column(nullable = false)
    private String originVideoName;

    @Column(nullable = false)
    private String encodingVideoName;

    @Column(nullable = false)
    private String savedVideoPath;

    @Builder
    public Video(Double x, Double y, Double width, Double height, Long videoSeq, String originVideoName, String encodingVideoName, String savedVideoPath) {
        super(x, y, width, height);
        this.videoSeq = videoSeq;
        this.originVideoName = originVideoName;
        this.encodingVideoName = encodingVideoName;
        this.savedVideoPath = savedVideoPath;
    }
}
