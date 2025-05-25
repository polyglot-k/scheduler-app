package com.example.scheduler.lv5.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 인증 관련
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // 조회 관련
    SCHEDULER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 일정 정보를 찾을 수 없습니다."),
    INVALID_EMAIL(HttpStatus.NOT_FOUND,"해당 이메일을 찾을 수 없습니다." ),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
    private final HttpStatus status;
    private final String message;

}
