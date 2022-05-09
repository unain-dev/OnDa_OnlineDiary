package com.ssafy.onda.api.member.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;

@ToString(of = { "email", "emailAuth" })
@Getter
public class ReqEmailAuthDto {

    @Email(regexp = "^[0-9a-z]+([.-]?[0-9a-z]+)*@[0-9a-z]+([.-]+[0-9a-z]+)*(\\.[0-9a-z]{2,3})+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String emailAuth;
}
