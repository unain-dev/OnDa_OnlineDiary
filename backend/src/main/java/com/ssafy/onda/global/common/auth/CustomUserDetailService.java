package com.ssafy.onda.global.common.auth;

import com.ssafy.onda.api.member.dto.MemberDto;
import com.ssafy.onda.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        // 이후에 Security 관련 작업하면 Dto로 수정
        MemberDto memberDto = memberService.findMemberDtoByMemberId(memberId);
        if(memberDto != null) {
            return new CustomUserDetails(memberDto);
        }
        return null;
    }
}
