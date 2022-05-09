package com.ssafy.onda.api.filter.controller;

import com.ssafy.onda.api.filter.dto.request.ReqMonthFilterDto;
import com.ssafy.onda.api.filter.service.FilterService;
import com.ssafy.onda.global.common.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/filter")
@RestController
public class FilterController {

    private final FilterService filterService;

    @GetMapping("{}")
    public ReqMonthFilterDto search(@RequestBody ReqMonthFilterDto reqMonthFilterDto){
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (reqMonthFilterDto.getDate() == null) {

        } else {
            filterService.MonthSearch(reqMonthFilterDto);
        }

        return reqMonthFilterDto;
    };

}
