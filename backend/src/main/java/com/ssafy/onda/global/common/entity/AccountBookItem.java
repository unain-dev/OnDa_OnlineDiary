package com.ssafy.onda.global.common.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"accountBookItemSeq", "description", "deposit", "withdraw"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_account_book_item")
@Entity
public class AccountBookItem {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long accountBookItemSeq;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long deposit;

    @Column(nullable = false)
    private Long withdraw;

    @JoinColumn(name = "account_book_seq", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountBook accountBook;

    @Builder
    public AccountBookItem(Long accountBookItemSeq, String description, Long deposit, Long withdraw, AccountBook accountBook) {
        this.accountBookItemSeq = accountBookItemSeq;
        this.description = description;
        this.deposit = deposit;
        this.withdraw = withdraw;
        this.accountBook = accountBook;
    }
}
