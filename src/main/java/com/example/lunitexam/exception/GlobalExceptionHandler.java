package com.example.lunitexam.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(LunitExamException.class)
    protected ResponseEntity<Object> handleCustomException(final LunitExamException e, HttpServletRequest request) {
        log.error("LunitExamException: {}", e.getMessage(), e);

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse(
                        e.getErrorCode().getCode(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e, HttpServletRequest request) {
        log.error("handleException: ", e);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .body(new ErrorResponse(
                        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        e.getMessage(),
                        request.getRequestURI()));
    }
}