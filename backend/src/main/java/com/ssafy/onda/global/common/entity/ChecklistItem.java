package com.ssafy.onda.global.common.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = { "checklistItemSeq", "isChecked", "content" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_checklist_item")
@Entity
public class ChecklistItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long checklistItemSeq;

    @Column(nullable = false)
    private Boolean isChecked;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "checklist_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Checklist checklist;

    @Builder
    public ChecklistItem(Long checklistItemSeq, Boolean isChecked, String content, Checklist checklist) {
        this.checklistItemSeq = checklistItemSeq;
        this.isChecked = isChecked;
        this.content = content;
        this.checklist = checklist;
    }
}
