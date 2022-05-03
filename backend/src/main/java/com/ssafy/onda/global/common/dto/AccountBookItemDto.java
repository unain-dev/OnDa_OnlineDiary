package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "description", "deposit", "withdraw" })
@NoArgsConstructor
@Getter
public class AccountBookItemDto {

    private String description;

    private Long deposit;

    private Long withdraw;

    @Builder
    public AccountBookItemDto(String description, Long deposit, Long withdraw) {
        this.description = description;
        this.deposit = deposit;
        this.withdraw = withdraw;
    }
}
