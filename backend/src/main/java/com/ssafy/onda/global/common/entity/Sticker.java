package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"stickerSeq", "stickerName", "stickerPath"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_sticker")
@Entity
public class Sticker extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long stickerSeq;

    @Column(nullable = false)
    private String stickerName;

    @Column(nullable = false)
    private String stickerPath;

    @Builder
    public Sticker(Long x, Long y, Long width, Long height, Long stickerSeq, String stickerName, String stickerPath) {
        super(x, y, width, height);
        this.stickerSeq = stickerSeq;
        this.stickerName = stickerName;
        this.stickerPath = stickerPath;
    }
}
