package com.ssafy.onda.api.diary.controller;

import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.dto.response.ResDiaryDto;
import com.ssafy.onda.api.diary.service.DiaryService;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.base.BaseResponseDto;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public BaseResponseDto save(Authentication authentication,
                                @Valid @RequestPart(value = "reqDiaryDto") ReqDiaryDto reqDiaryDto,
                                Errors errors,
                                @RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles) {
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
            diaryService.save(details, reqDiaryDto, multipartFiles);

            status = CREATED.value();
            msg = "다이어리 저장 성공";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .data(data)
                .build();
    }

    @DeleteMapping("/{diaryDate}")
    public BaseResponseDto delete(Authentication authentication, @PathVariable String diaryDate) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        diaryService.delete(details, diaryDate);

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("다이어리 삭제 성공")
                .build();
    }

    @GetMapping("/{diaryDate}")
    public BaseResponseDto load(Authentication authentication, @PathVariable String diaryDate) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        CustomUserDetails details = (CustomUserDetails) authentication.getDetails();
        ResDiaryDto resDiaryDto = diaryService.load(details, diaryDate);

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("다이어리 불러오기 성공")
                .data(new HashMap<>() {{
                    put("diaryDate", resDiaryDto.getDiaryDate());
                    put("totalCnt", resDiaryDto.getTotalCnt());
                    put("memoList", resDiaryDto.getMemoList());
                }})
                .build();
    }

    @GetMapping("/calendar/{diaryDate}")
    public BaseResponseDto calendar(Authentication authentication, @PathVariable String diaryDate) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("달력 조회 성공")
                .data(new HashMap<>() {{
                    put("days", diaryService.getDays((CustomUserDetails) authentication.getDetails(), diaryDate));
                }})
                .build();
    }
}
