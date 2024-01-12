package com.example.lunitexam.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int code;
    private final String message;
    private final String path;

    public ErrorResponse(int code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
    }
}