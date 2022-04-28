package com.ssafy.onda.api.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString(of = { "memberId", "email", "nickname" })
@Getter
public class ResMemberDto {

    private String memberId;

    private String email;

    private String nickname;

    @Builder
    public ResMemberDto(String memberId, String email, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
    }
}
