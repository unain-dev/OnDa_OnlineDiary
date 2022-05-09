package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "content", "income", "outcome" })
@NoArgsConstructor
@Getter
public class AccountBookItemDto {

    private String content;

    private String income;

    private String outcome;

    @Builder
    public AccountBookItemDto(String content, String income, String outcome) {
        this.content = content;
        this.income = income;
        this.outcome = outcome;
    }
}
