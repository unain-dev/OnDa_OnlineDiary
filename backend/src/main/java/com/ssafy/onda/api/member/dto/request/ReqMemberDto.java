package com.ssafy.onda.api.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class ReqMemberDto {

    @Pattern(regexp = "^[a-z]+[0-9a-z]{3,15}$", message = "아이디 형식에 맞지 않습니다.")
    private String memberId;

    @Pattern(regexp = "([0-9]+[A-Z]+|[A-Z]+[0-9])+[0-9a-zA-Z!@#$%]*|([0-9]+[a-z]+|[a-z]+[0-9])+[0-9a-zA-Z!@#$%]*|([0-9]+[!@#$%]+|[!@#$%]+[0-9])+[0-9a-zA-Z!@#$%]*|([A-Z]+[a-z]+|[a-z]+[A-Z])+[0-9a-zA-Z!@#$%]*|([A-Z]+[!@#$%]+|[!@#$%]+[A-Z])+[0-9a-zA-Z!@#$%]*|([a-z]+[!@#$%]+|[!@#$%]+[a-z])+[0-9a-zA-Z!@#$%]*", message = "패스워드 형식에 맞지 않습니다.")
    @Length(min = 8, max = 16, message = "패스워드는 8자 이상 16자 이하로 입력해주세요.")
    private String password;

    @Email(regexp = "^[0-9a-z]+([.-]?[0-9a-z]+)*@[0-9a-z]+([.-]+[0-9a-z]+)*(\\.[0-9a-z]{2,3})+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{3,12}$", message = "닉네임 형식에 맞지 않습니다.")
    private String nickname;

    @Builder
    public ReqMemberDto(String memberId, String password, String email, String nickname) {
        this.memberId = memberId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }
}
