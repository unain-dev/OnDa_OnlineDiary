package com.ssafy.onda.api.member.service;

import com.ssafy.onda.api.member.dto.MemberDto;
import com.ssafy.onda.api.member.dto.request.ReqLoginMemberDto;
import com.ssafy.onda.api.member.dto.request.ReqMemberDto;
import com.ssafy.onda.api.member.entity.Member;
import com.ssafy.onda.api.member.repository.MemberRepository;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean hasMemberId(String memberId) {

        String regx = "^[a-z]+[0-9a-z]{3,15}$";
        Pattern pattern = Pattern.compile(regx);

        if (!pattern.matcher(memberId).matches()) {
            throw new CustomException(LogUtil.getElement(), INVALID_ID_FORMAT);
        }

        return memberRepository.existsByMemberId(memberId);
    }

    @Override
    public boolean hasEmail(String email) {

        String regx = "^[0-9a-z]+([.-]?[0-9a-z]+)*@[0-9a-z]+([.-]+[0-9a-z]+)*(\\.[0-9a-z]{2,3})+$";
        Pattern pattern = Pattern.compile(regx);

        if (!pattern.matcher(email).matches()) {
            throw new CustomException(LogUtil.getElement(), INVALID_EMAIL_FORMAT);
        }

        return memberRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void register(ReqMemberDto reqMemberDto) {

        String memberId = reqMemberDto.getMemberId();

        // validation & duplication check
        if (hasMemberId(memberId)) {
            throw new CustomException(LogUtil.getElement(), MEMBERID_DUPLICATION);
        } else if (reqMemberDto.getPassword().contains(memberId)) {
            throw new CustomException(LogUtil.getElement(), PASSWORD_CONTAINED_MEMBERID);
        } else if (hasEmail(reqMemberDto.getEmail())) {
            throw new CustomException(LogUtil.getElement(), EMAIL_DUPLICATION);
        }

        String encryptedPassword = passwordEncoder.encode(reqMemberDto.getPassword());

        memberRepository.save(Member.builder()
                .memberId(memberId)
                .password(encryptedPassword)
                .email(reqMemberDto.getEmail())
                .nickname(reqMemberDto.getNickname())
                .build());
    }

    @Override
    public MemberDto findMemberDtoInLogin(ReqLoginMemberDto reqLoginMemberDto) {

        MemberDto memberDto = memberRepository.findMemberDtoByMemberId(reqLoginMemberDto.getMemberId())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBERID_NOT_FOUND));

        if (!passwordEncoder.matches(reqLoginMemberDto.getPassword(), memberDto.getPassword())) {
            throw new CustomException(LogUtil.getElement(), PASSWORD_NOT_MATCH);
        }

        return memberDto;
    }

    @Override
    public MemberDto findMemberDtoByMemberId(String memberId) {
        return memberRepository.findMemberDtoByMemberId(memberId)
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));
    }
}
