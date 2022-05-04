package com.ssafy.onda.global.common.dto;

import lombok.*;

@ToString(of = { "content", "income", "outcome" })
@NoArgsConstructor
@Getter
public class AccountBookItemDto {

    private String content;

    private Long income;

    private Long outcome;

    @Builder
    public AccountBookItemDto(String content, Long income, Long outcome) {
        this.content = content;
        this.income = income;
        this.outcome = outcome;
    }
}
