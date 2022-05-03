package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"textSeq", "textHeader", "textContent"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_text")
@Entity
public class Text extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long textSeq;

    @Column(nullable = false)
    private String textHeader;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String textContent;

    @Builder
    public Text(Long x, Long y, Long width, Long height, Long textSeq, String textHeader, String textContent) {
        super(x, y, width, height);
        this.textSeq = textSeq;
        this.textHeader = textHeader;
        this.textContent = textContent;
    }
}
