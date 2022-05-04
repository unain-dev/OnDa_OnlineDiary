package com.ssafy.onda.global.common.entity;

import com.ssafy.onda.global.common.entity.base.BaseMemoEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = { "accountBookSeq" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_account_book")
@Entity
public class AccountBook extends BaseMemoEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long accountBookSeq;

    @Builder
    public AccountBook(Long x, Long y, Long width, Long height, Long accountBookSeq) {
        super(x, y, width, height);
        this.accountBookSeq = accountBookSeq;
    }
}
