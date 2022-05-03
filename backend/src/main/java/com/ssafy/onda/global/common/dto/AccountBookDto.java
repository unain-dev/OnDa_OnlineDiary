package com.ssafy.onda.global.common.dto;

import lombok.*;

import java.util.List;

@ToString(of = { "totalAmount", "totalAmount", "totalWithdraw", "accountBookItems" })
@NoArgsConstructor
@Getter
public class AccountBookDto {

    private Long totalAmount;

    private Long totalDeposit;

    private Long totalWithdraw;

    private List<AccountBookItemDto> accountBookItems;

    @Builder
    public AccountBookDto(Long totalAmount, Long totalDeposit, Long totalWithdraw, List<AccountBookItemDto> accountBookItems) {
        this.totalAmount = totalAmount;
        this.totalDeposit = totalDeposit;
        this.totalWithdraw = totalWithdraw;
        this.accountBookItems = accountBookItems;
    }
}
