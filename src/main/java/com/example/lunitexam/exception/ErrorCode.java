package com.example.lunitexam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    OK(0, "success", HttpStatus.OK),
    NOT_FOUND(10, "data does not exist", HttpStatus.NOT_FOUND),
    BAD_REQUEST(20, "invalid argument", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_DATA(21, "invalid data format", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_GRID_RESULT_DATA(22, "invalid grid data length", HttpStatus.EXPECTATION_FAILED),
    INTERNAL_SERVER_ERROR(30, "internal server error", HttpStatus.INTERNAL_SERVER_ERROR);



    private final Integer code;
    private String message;
    private final HttpStatus httpStatus;

    public void setMessage(String message) {
        this.message = message;
    }

}