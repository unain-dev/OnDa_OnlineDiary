package com.ssafy.onda.api.filter.controller;

import com.ssafy.onda.api.filter.service.FilterService;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.base.BaseResponseDto;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/filter")
@RestController
public class FilterController {

    private final FilterService filterService;

    @GetMapping
    public BaseResponseDto searchFilter(Authentication authentication,
                                        @RequestParam int type,
                                        @RequestParam(required = false) String keyword) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("데이터 필터링 완료")
                .data(new HashMap<>() {{
                    put("monthFilter", filterService.search(type, keyword, details));
                }})
                .build();
    }

    @GetMapping("/preview")
    public BaseResponseDto preview(Authentication authentication,
                                   @RequestParam int memoTypeSeq,
                                   @RequestParam String memoSeqList) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("미리보기 로드 성공")
                .data(new HashMap<>() {{
                    put("memoList", filterService.preview((CustomUserDetails) authentication.getDetails(), memoTypeSeq, memoSeqList));
                }})
                .build();
    }
}
