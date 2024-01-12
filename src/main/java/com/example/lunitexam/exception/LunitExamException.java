package com.example.lunitexam.exception;

import lombok.Getter;

@Getter
public class LunitExamException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public LunitExamException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public LunitExamException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}