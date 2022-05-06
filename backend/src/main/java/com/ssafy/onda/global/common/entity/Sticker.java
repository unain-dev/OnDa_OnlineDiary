package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"stickerSeq", "emoji" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_sticker")
@Entity
public class Sticker extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long stickerSeq;

    @Column(nullable = false)
    private String emoji;

    @Builder
    public Sticker(Long x, Long y, Long width, Long height, Long stickerSeq, String emoji) {
        super(x, y, width, height);
        this.stickerSeq = stickerSeq;
        this.emoji = emoji;
    }
}
