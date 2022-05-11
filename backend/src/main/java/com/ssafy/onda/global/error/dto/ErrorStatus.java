package com.ssafy.onda.global.error.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorStatus {

    // Common
    INVALID_INPUT_VALUE(BAD_REQUEST.value(), "올바르지 않은 요청입니다."),
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
    MEMBERID_DUPLICATION(CONFLICT.value(), "이미 존재하는 ID입니다."),
    EMAIL_DUPLICATION(CONFLICT.value(), "해당 이메일로 가입한 계정이 존재합니다."),         // 409, "Email is Duplication"
    NICKNAME_DUPLICATION(CONFLICT.value(), "이미 사용중인 닉네임입니다."),    // 409, "Steam ID is Duplication"
    PASSWORD_CONTAINED_MEMBERID(BAD_REQUEST.value(), "패스워드에 ID가 포함될 수 없습니다."),          // "ssgameId into password"
    FAIL_TO_REGISTER(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원가입 실패"),
    MEMBER_NOT_FOUND(BAD_REQUEST.value(), "사용자가 존재하지 않습니다."),
    // id check
    INVALID_ID_FORMAT(BAD_REQUEST.value(), "ID 형식에 맞지 않습니다."),
    INVALID_EMAIL_FORMAT(BAD_REQUEST.value(), "이메일 형식에 맞지 않습니다."),
    // login
    LOGIN_INPUT_INVALID(BAD_REQUEST.value(), "Login input is invalid"),
    MEMBERID_NOT_FOUND(BAD_REQUEST.value(), "ID를 다시 확인해주세요."),
    PASSWORD_NOT_MATCH(UNAUTHORIZED.value(), "비밀번호를 다시 확인해주세요."),

    // diary
    INVALID_DATE_FORMAT(BAD_REQUEST.value(), "날짜를 다시 확인해주세요."),
    INVALID_MEMO_TYPE(BAD_REQUEST.value(), "올바르지 않은 요청입니다."),
    BACKGROUND_NOT_FOUND(NO_CONTENT.value(), "해당 날짜에 작성한 다이어리가 존재하지 않습니다."),
    NO_MEMO_AVAILABLE(BAD_REQUEST.value(), "저장 가능한 메모가 없습니다."),

    // file save
    NO_DATA_TO_SAVE(BAD_REQUEST.value(), "빈 메모는 저장이 불가능합니다."),
    FAIL_TO_SAVE_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 저장 실패"),
    MISMATCH_IN_NUMBER_OF_FILES_AND_IMAGES(BAD_REQUEST.value(), "전송된 파일과 이미지 메모지의 개수가 일치하지 않습니다."),
    FILE_INDEX_OUT_OF_BOUNDS(BAD_REQUEST.value(), "파일 인덱스 초과"),

    // FILTER
    ACCESS_DENIED(UNAUTHORIZED.value(), "데이터 접근 불가")
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
