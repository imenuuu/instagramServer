package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    CREATE_FAIL_HIGHLIGHT(false,2004,"하이라이트 생성에 실패했습니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EMPTY_NICKNAME(false,2020,"닉네임을 입력해주세요"),
    POST_USERS_EMPTY_PHONE_NUMBER(false,2030,"휴대폰 번호를 입력해주세요"),
    POST_USERS_INVALID_PHONE_NUMBER(false,2031,"휴대폰형식과 맞지않음"),

    EXISTS_FOLLOW(false,2032,"이미 팔로우 한 유저 입니다."),



    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    SEARCH_RESULT_NO(false,3001,"검색 결과가 없습니다."),

    // [POST] /users
    USERS_EXISTS_NICKNAME(false,3011,"이미 존재하는 닉네임입니다."),
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),

    // logIn Validation
    FAILED_TO_LOGIN_EMAILNULL(false,3012,"이메일 을 입력해 주세요"),
    FAILED_TO_LOGIN(false,3014,"없는 이메일이거나 비밀번호가 틀렸습니다."),
    FAILED_TO_LOGIN_PWDNULL(false,3015,"비밀번호 를 입력해 주세요"),
    FAILED_TO_LOGIN_USERSTATUS(false,3016,"비활성상태입니다. 로그인하려면 계속하기를 눌러주세요"),


    FAILED_TO_GETPROFILE(false,3017,"유저 정보를 찾을 수 없습니다"),
    FAILED_TO_GETPROFILE_NO_NAME(false,3018,"유저 닉네임이 존재하지 않습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저정보 수정 실패"),
    MODIFY_FAIL_CREATECOMMENT(false,4015,"댓글 생성 실패"),
    MODIFY_FAIL_USERSTATUS(false,4017,"유저 비활성화 실패"),

    DELETE_FAIL_COMMENT(false,4016,"댓글 삭제 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),


    // 5000 : 필요시 만들어서 쓰세요

    LOGOUT(false,5000,"로그아웃 되었씁니다.");

    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
