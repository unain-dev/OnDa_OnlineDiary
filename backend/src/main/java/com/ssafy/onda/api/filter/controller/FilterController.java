package com.ssafy.onda.api.filter.controller;

import com.ssafy.onda.api.filter.service.FilterService;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.base.BaseResponseDto;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.ssafy.onda.global.error.dto.ErrorStatus.UNAUTHORIZED_ACCESS;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/filter")
@RestController
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/")
    public BaseResponseDto searchFilter(Authentication authentication,
                             @RequestParam(required = false) Long type,
                             @RequestParam(required = false) String keyword){

        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();

        log.info("detail : " + details.getUsername());

        HashMap<String, Object> data = new HashMap<>();

        if(keyword == null) {
            data.put("monthFilter", filterService.search(type, details));
        } else{
            data.put("monthFilter", filterService.searchBox(type, keyword, details));
        }

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("데이터 필터링 완료")
                .data(data)
                .build();
    }
}
