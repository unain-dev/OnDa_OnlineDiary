package com.ssafy.onda.api.member.controller;

import com.ssafy.onda.api.member.dto.request.ReqMemberDto;
import com.ssafy.onda.api.member.service.MemberService;
import com.ssafy.onda.global.common.dto.BaseResponseDto;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponseDto register(@Valid @RequestBody ReqMemberDto reqMemberDto, Errors errors) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

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
            memberService.register(reqMemberDto);
            status = CREATED.value();
            msg = "회원가입 성공";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .data(data)
                .build();
    }

    @GetMapping("/memberId/{memberId}")
    public BaseResponseDto isExistMemberId(@PathVariable String memberId) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        String msg = null;

        if (memberService.hasMemberId(memberId)) {
            status = OK.value();
            msg = "이미 존재하는 ID입니다.";
        } else {
            status = NO_CONTENT.value();
            msg = "사용할 수 있는 ID입니다.";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .build();
    }

    @GetMapping("/email/{email}")
    public BaseResponseDto isExistEmail(@PathVariable String email) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        String msg = null;

        if (memberService.hasEmail(email)) {
            status = OK.value();
            msg = "이미 사용중인 이메일입니다.";
        } else {
            status = NO_CONTENT.value();
            msg = "사용할 수 있는 이메일입니다.";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .build();
    }
}
