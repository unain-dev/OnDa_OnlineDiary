package com.ssafy.onda.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString(of = { "accountBookItems" })
@NoArgsConstructor
@Getter
public class AccountBookDto {

    private List<AccountBookItemDto> accountBookItems;

    @Builder
    public AccountBookDto(List<AccountBookItemDto> accountBookItems) {
        this.accountBookItems = accountBookItems;
    }
}
