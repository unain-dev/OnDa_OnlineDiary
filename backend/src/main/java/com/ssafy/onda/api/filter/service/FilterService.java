package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.api.filter.dto.request.ReqMonthFilterDto;

public interface FilterService {

    void monthSearch(ReqMonthFilterDto reqMonthFilterDto);
}
