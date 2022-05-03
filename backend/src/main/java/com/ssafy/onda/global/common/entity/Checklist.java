package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"checklistSeq", "checklistHeader"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_checklist")
@Entity
public class Checklist extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long checklistSeq;

    @Column(nullable = false)
    private String checklistHeader;

    @Builder
    public Checklist(Long x, Long y, Long width, Long height, Long checklistSeq, String checklistHeader) {
        super(x, y, width, height);
        this.checklistSeq = checklistSeq;
        this.checklistHeader = checklistHeader;
    }
}
