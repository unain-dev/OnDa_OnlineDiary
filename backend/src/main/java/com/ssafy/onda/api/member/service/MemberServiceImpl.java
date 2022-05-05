package com.ssafy.onda.api.member.service;

import com.ssafy.onda.api.member.dto.MemberDto;
import com.ssafy.onda.api.member.dto.request.ReqLoginMemberDto;
import com.ssafy.onda.api.member.dto.request.ReqMemberDto;
import com.ssafy.onda.api.member.dto.request.ReqUpdatePasswordDto;
import com.ssafy.onda.api.member.dto.response.ResMemberDto;
import com.ssafy.onda.api.member.entity.EmailAuth;
import com.ssafy.onda.api.member.entity.Member;
import com.ssafy.onda.api.member.repository.EmailAuthRepository;
import com.ssafy.onda.api.member.repository.MemberRepository;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthRepository emailAuthRepository;

    @Autowired
    private final JavaMailSender javaMailSender;

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

    @Override
    public ResMemberDto findResMemberDto(CustomUserDetails details) {

        MemberDto memberDto = memberRepository.findMemberDtoByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        return ResMemberDto.builder()
                .memberId(memberDto.getMemberId())
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .build();
    }

    @Transactional
    @Override
    public void updateMemberPassword(CustomUserDetails details, ReqUpdatePasswordDto reqUpdatePasswordDto) {

        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        String password = member.getPassword();
        if (!passwordEncoder.matches(reqUpdatePasswordDto.getPrePassword(), password)) {
            throw new CustomException(LogUtil.getElement(), PASSWORD_NOT_MATCH);
        }

        if (reqUpdatePasswordDto.getNewPassword().contains(member.getMemberId())) {
            throw new CustomException(LogUtil.getElement(), PASSWORD_CONTAINED_MEMBERID);
        }

        member.changePassword(passwordEncoder.encode(reqUpdatePasswordDto.getNewPassword()));
    }

    @Transactional
    @Override
    public void authEmail(String userEmail) {
        //인증 번호 생성기
        StringBuffer temp =new StringBuffer();
        Random rnd = new Random();
        for(int i=0;i<10;i++)
        {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        String AuthenticationKey = temp.toString();
        LocalDateTime now = LocalDateTime.now();

        //메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setFrom("hush8541@gmail.com");
        message.setSubject("다꾸다꾸 가입 인증");
        message.setText(
                "다이어리 꾸미기 다꾸다꾸에 가입 신청해주셔서 감사합니다 "
                + "\n 인증번호 : "
                + AuthenticationKey
        );

        EmailAuth emailAuth = new EmailAuth(userEmail,AuthenticationKey,LocalDateTime.now());

        emailAuthRepository.deleteAllInBatch(new ArrayList<>(){{
            add(emailAuthRepository.findByEmail(userEmail));
        }});
        log.info("------------------------------------");
        emailAuthRepository.save(emailAuth);
        log.info("------------------------------------");

        javaMailSender.send(message);

    }

    @Transactional
    @Override
    public boolean authEmailCheck(String userEmail, String Auth) {

        String AuthTemp = emailAuthRepository.findByEmail(userEmail).getEmailAuth();

        if(AuthTemp.equals(Auth)) {
            emailAuthRepository.deleteById(emailAuthRepository.findByEmail(userEmail).getEmailAuthSeq());
            return true;
        } else{
            return false;
        }
    }

}
