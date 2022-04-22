package com.ssafy.onda.global.common.auth;

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
    //	@Autowired
//    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String ssgameId) throws UsernameNotFoundException {
        // 이후에 Security 관련 작업하면 Dto로 수정
//        Member member = memberService.findMemberBySsgameId(ssgameId);
//        if(member != null) {
//            return new CustomUserDetails(member);
//        }
        return null;
    }
}
