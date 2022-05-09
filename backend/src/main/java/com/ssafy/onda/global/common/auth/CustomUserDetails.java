package com.ssafy.onda.global.common.auth;

import com.ssafy.onda.api.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 부가 상세정보(활성화 여부, 만료, 롤 등) 정의.
 */
public class CustomUserDetails implements UserDetails {

    @Autowired
    MemberDto memberDto;

    boolean accountNonExpired;

    boolean accountNonLocked;

    boolean credentialNonExpired;

    boolean enabled = false;

    List<GrantedAuthority> roles = new ArrayList<>();

    public CustomUserDetails(MemberDto memberDto) {
        super();
        this.memberDto = memberDto;
    }

    public MemberDto getMemberDto() {
        return this.memberDto;
    }

    @Override
    public String getPassword() {
        return this.memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return this.memberDto.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public void setAuthorities(List<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public String getEmail() { return this.memberDto.getEmail(); }
}
