package com.ssafy.onda.api.member.controller;

import com.ssafy.onda.api.member.dto.MemberDto;
import com.ssafy.onda.api.member.dto.request.*;
import com.ssafy.onda.api.member.service.MemberService;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.base.BaseResponseDto;
import com.ssafy.onda.global.common.util.JwtTokenUtil;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.ssafy.onda.global.error.dto.ErrorStatus.GLOBAL_ERROR;
import static com.ssafy.onda.global.error.dto.ErrorStatus.UNAUTHORIZED_ACCESS;
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

    @PostMapping("/email/auth")
    private BaseResponseDto sendEmail(@RequestBody ReqEmailAuthDto reqEmailAuthDto){
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        String msg = null;

        if (memberService.hasEmail(reqEmailAuthDto.getEmail())) {
            status = OK.value();
            msg = "이미 사용중인 이메일입니다.";
        } else {
            memberService.authEmail(reqEmailAuthDto.getEmail());
            status = NO_CONTENT.value();
            msg = "인증번호를 전송하였습니다.";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .build();
    }

    @PostMapping("/email/auth/check")
    private BaseResponseDto sendEmailCheck(@RequestBody ReqEmailAuthDto reqEmailAuthDto){
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        Integer status = null;
        String msg = null;

        boolean authCheck = memberService.authEmailCheck(reqEmailAuthDto.getEmail(), reqEmailAuthDto.getEmailAuth());

        if (authCheck) {
            status = OK.value();
            msg = "이메일 인증에 성공하였습니다.";
        } else {
            status = NO_CONTENT.value();
            msg = "이메일 인증에 실패하였습니다.";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .build();
    }

    @PostMapping("/login")
    public BaseResponseDto login(@Valid @RequestBody ReqLoginMemberDto reqLoginMemberDto, Errors errors) {
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
            MemberDto memberDto = memberService.findMemberDtoInLogin(reqLoginMemberDto);
            String jwtToken = JwtTokenUtil.getToken(memberDto.getMemberId());

            status = OK.value();
            msg = "로그인 성공";

            data.put("memberSeq", memberDto.getMemberSeq());
            data.put("memberId", memberDto.getMemberId());
            data.put("nickname", memberDto.getNickname());
            data.put("jwtToken", jwtToken);
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .data(data)
                .build();
    }

    @GetMapping("/mypage")
    public BaseResponseDto mypage(Authentication authentication) {
        log.info("Called API: {}", LogUtil.getClassAndMethodName());

        if (authentication == null) {
            throw new CustomException(LogUtil.getElement(), UNAUTHORIZED_ACCESS);
        }

        return BaseResponseDto.builder()
                .status(OK.value())
                .msg("회원정보 조회에 성공했습니다.")
                .data(new HashMap<>(){{
                    put("memberInfo", memberService.findResMemberDto((CustomUserDetails) authentication.getDetails()));
                }})
                .build();
    }

    @PutMapping("/mypage/password")
    public BaseResponseDto updatePassword(Authentication authentication, @Valid @RequestBody ReqUpdatePasswordDto reqUpdatePasswordDto, Errors errors) {
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
            memberService.updateMemberPassword(details, reqUpdatePasswordDto);

            status = OK.value();
            msg = "비밀번호 변경 성공";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .data(data)
                .build();
    }

    @PutMapping("/mypage/info")
    public BaseResponseDto updateUserInfo(Authentication authentication, @Valid @RequestBody ReqUserInfoDto reqUserInfoDto, Errors errors) {
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
            memberService.changeInfo(details.getEmail(), reqUserInfoDto.getNickname());
            status = OK.value();
            msg = "회원 정보 변경 성공";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .data(data)
                .build();
    }

    @DeleteMapping
    public BaseResponseDto delete(Authentication authentication, @Valid @RequestBody ReqLoginMemberDto reqLoginMemberDto, Errors errors) {
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
            memberService.delete(details, reqLoginMemberDto);

            status = OK.value();
            msg = "회원 탈퇴 성공";
        }

        return BaseResponseDto.builder()
                .status(status)
                .msg(msg)
                .build();
    }
}
