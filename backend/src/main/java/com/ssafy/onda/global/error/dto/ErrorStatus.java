package com.ssafy.onda.global.error.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * HttpStatus.NO_CONTENT 사용 자제
 * 프론트까지 결과가 송출되지 않음
 */
@Getter
public enum ErrorStatus {

    // Common
    INVALID_INPUT_VALUE(BAD_REQUEST.value(), " Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), " Invalid Input Value"),
    ENTITY_NOT_FOUND(BAD_REQUEST.value(), "해당 정보가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error"),
    INVALID_TYPE_VALUE(BAD_REQUEST.value(), " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(FORBIDDEN.value(), "Access is Denied"),
    GLOBAL_ERROR(BAD_REQUEST.value(), "Global Error"),
    //    FIELD_ERROR(HttpStatus.BAD_REQUEST.value(), "Field Error in Post Mapping"),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED.value(), "인증 오류"),
    JSON_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 데이터 오류"),

    // Member
    // register
    SSGAMEID_DUPLICATION(CONFLICT.value(), "이미 존재하는 ID입니다."),
    EMAIL_DUPLICATION(CONFLICT.value(), "해당 이메일로 가입한 계정이 존재합니다."),         // 409, "Email is Duplication"
    STEAMID_DUPLICATION(CONFLICT.value(), "해당 Steam ID로 가입한 계정이 존재합니다."),    // 409, "Steam ID is Duplication"
    PASSWORD_CONTAINED_SSGAMEID(BAD_REQUEST.value(), "패스워드에 ID가 포함될 수 없습니다."),          // "ssgameId into password"
    FAIL_TO_REGISTER(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원가입 실패"),
    MEMBER_NOT_FOUND(BAD_REQUEST.value(), "사용자가 존재하지 않습니다."),
    // id check
    INVALID_ID_FORMAT(BAD_REQUEST.value(), "ID 형식에 맞지 않습니다."),
    // login
    LOGIN_INPUT_INVALID(BAD_REQUEST.value(), "Login input is invalid"),
    SSGAMEID_NOT_FOUND(BAD_REQUEST.value(), "ID를 다시 확인해주세요."),
    PASSWORD_NOT_MATCH(UNAUTHORIZED.value(), "비밀번호를 다시 확인해주세요."),
    // update steam id
    SAME_STEAM_ID(BAD_REQUEST.value(), "동일한 Steam ID로는 변경이 불가능합니다."),
    FAIL_TO_UPDATE_STEAMID(HttpStatus.INTERNAL_SERVER_ERROR.value(), "steamID 수정 실패"),
    PRIVATE_STEAMID(BAD_REQUEST.value(), "Steam ID의 게임 공개 설정을 확인해주세요."),

    // API
    API_NOT_CONNECTION(BAD_GATEWAY.value(), "Fail to connect"),
    INVALID_STEAMID(BAD_REQUEST.value(), "확인되지 않는 Steam ID입니다."),

    // Game Info
    GAME_NOT_FOUND(BAD_GATEWAY.value(), "게임 정보가 존재하지 않습니다."),
    INVALID_RANGE_OF_RATING(BAD_REQUEST.value(), "별점은 1이상 5이하의 정수입니다."),

    // RecommendedGame
    LACK_OF_RECOMMENDED_GAME(FAILED_DEPENDENCY.value(), "추천 게임 목록 개수가 부족합니다."),
    NO_GAME_PLAYED(NO_CONTENT.value(), "사용자가 플레이한 게임이 없습니다.")
    ;

    private Integer status;

    private Integer backToFrontStatus;

    private final String msg;

    ErrorStatus(Integer backToFrontStatus, String msg) {
        this.status = OK.value();
        this.backToFrontStatus = backToFrontStatus;
        this.msg = msg;
    }
}
