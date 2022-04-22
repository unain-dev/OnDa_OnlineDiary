package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"textSeq", "textContent"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_text")
@Entity
public class Text extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long textSeq;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String textContent;

    @Builder
    public Text(Double x, Double y, Double width, Double height, Long textSeq, String textContent) {
        super(x, y, width, height);
        this.textSeq = textSeq;
        this.textContent = textContent;
    }
}
