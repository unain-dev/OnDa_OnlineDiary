package com.ssafy.onda.api.filter.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString(of = { "date", "type", "keyword" })
@NoArgsConstructor
@Getter
public class ReqMonthFilterDto {

    private Date date;

    private Long type;

    private String keyword;
}
