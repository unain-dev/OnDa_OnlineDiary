package com.ssafy.onda.api.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString(of = { "memberSeq", "memberId", "password", "email", "nickname" })
@Getter
public class MemberDto {

    private Long memberSeq;

    private String memberId;

    private String password;

    private String email;

    private String nickname;

    @Builder
    public MemberDto(Long memberSeq, String memberId, String password, String email, String nickname) {
        this.memberSeq = memberSeq;
        this.memberId = memberId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }
}
