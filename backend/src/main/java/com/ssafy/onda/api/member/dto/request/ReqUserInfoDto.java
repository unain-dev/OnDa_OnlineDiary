package com.ssafy.onda.api.member.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@ToString
public class ReqUserInfoDto {

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{3,12}$", message = "닉네임 형식에 맞지 않습니다.")
    private String nickname;

}
