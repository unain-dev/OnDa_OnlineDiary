package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"accountBookSeq", "totalAmount", "totalDeposit", "totalWithdraw"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_account_book")
@Entity
public class AccountBook extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long accountBookSeq;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private Long totalDeposit;

    @Column(nullable = false)
    private Long totalWithdraw;

    @Builder
    public AccountBook(Long x, Long y, Long width, Long height, Long accountBookSeq, Long totalAmount, Long totalDeposit, Long totalWithdraw) {
        super(x, y, width, height);
        this.accountBookSeq = accountBookSeq;
        this.totalAmount = totalAmount;
        this.totalDeposit = totalDeposit;
        this.totalWithdraw = totalWithdraw;
    }
}
