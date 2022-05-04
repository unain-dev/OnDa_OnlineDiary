package com.ssafy.onda.global.common.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"accountBookItemSeq", "content", "income", "outcome"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_account_book_item")
@Entity
public class AccountBookItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long accountBookItemSeq;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long income;

    @Column(nullable = false)
    private Long outcome;

    @JoinColumn(name = "account_book_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountBook accountBook;

    @Builder
    public AccountBookItem(Long accountBookItemSeq, String content, Long income, Long outcome, AccountBook accountBook) {
        this.accountBookItemSeq = accountBookItemSeq;
        this.content = content;
        this.income = income;
        this.outcome = outcome;
        this.accountBook = accountBook;
    }
}
