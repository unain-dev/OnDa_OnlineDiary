package com.ssafy.onda.api.diary.controller;

import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.service.DiaryService;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.base.BaseResponseDto;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public BaseResponseDto save(Authentication authentication, @Valid @RequestBody ReqDiaryDto reqDiaryDto, Errors errors) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        Integer status = null;
        String msg = null;
        Map<String, Object> data = new HashMap<>();

        if (errors.hasErrors()) {
            if (errors.hasFieldErrors()) {
                status = BAD_REQUEST.value();
                data.put("field", errors.getFieldError().getField());
                msg = errors.getFieldError().getDefaultMessage();
            } else {
                throw new CustomException(LogUtil.getElement(), GLOBAL_ERROR);
            }
        } else {
            CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
            diaryService.save(details, reqDiaryDto);

            status = OK.value();
            msg = "다이어리 저장 성공";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .data(data)
                .build();
    }

}
