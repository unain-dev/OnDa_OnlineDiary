package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"textSeq", "header", "content"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_text")
@Entity
public class Text extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long textSeq;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Builder
    public Text(Long x, Long y, Long width, Long height, Long textSeq, String header, String content) {
        super(x, y, width, height);
        this.textSeq = textSeq;
        this.header = header;
        this.content = content;
    }
}
