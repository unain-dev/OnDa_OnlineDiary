package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"imageSeq"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_image")
@Entity
public class Image extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long imageSeq;

    @Column(nullable = false)
    private String originImageName;

    @Column(nullable = false)
    private String encodingImageName;

    @Column(nullable = false)
    private String savedImagePath;

    @Builder
    public Image(Double x, Double y, Double width, Double height, Long imageSeq, String originImageName, String encodingImageName, String savedImagePath) {
        super(x, y, width, height);
        this.imageSeq = imageSeq;
        this.originImageName = originImageName;
        this.encodingImageName = encodingImageName;
        this.savedImagePath = savedImagePath;
    }
}
