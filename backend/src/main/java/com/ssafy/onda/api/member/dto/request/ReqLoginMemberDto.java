package com.ssafy.onda.api.member.dto.request;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@ToString(of = { "memberId", "password" })
@Getter
public class ReqLoginMemberDto {

    @Pattern(regexp = "^[a-z]+[0-9a-z]{3,15}$", message = "아이디 형식에 맞지 않습니다.")
    private String memberId;

    @Pattern(regexp = "([0-9]+[A-Z]+|[A-Z]+[0-9])+[0-9a-zA-Z!@#$%]*|([0-9]+[a-z]+|[a-z]+[0-9])+[0-9a-zA-Z!@#$%]*|([0-9]+[!@#$%]+|[!@#$%]+[0-9])+[0-9a-zA-Z!@#$%]*|([A-Z]+[a-z]+|[a-z]+[A-Z])+[0-9a-zA-Z!@#$%]*|([A-Z]+[!@#$%]+|[!@#$%]+[A-Z])+[0-9a-zA-Z!@#$%]*|([a-z]+[!@#$%]+|[!@#$%]+[a-z])+[0-9a-zA-Z!@#$%]*", message = "패스워드 형식에 맞지 않습니다.")
    @Length(min = 8, max = 16, message = "패스워드는 8자 이상 16자 이하로 입력해주세요.")
    private String password;

}
