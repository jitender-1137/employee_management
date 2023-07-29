package com.universal.em.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto<T> implements ResponseDto<T> {

    private String code = "";
    private T data;
    private List<String> message;
    private Long timestamp;
    private String requestId;

    public ErrorResponseDto(String errorMessage) {
        this.message = Collections.singletonList(errorMessage);
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = Collections.singletonList(errorMessage);
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponseDto(String code, T data, String errorMessage) {
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.data = data;
        this.message = Collections.singletonList(errorMessage);
    }

    public ErrorResponseDto(String code, List<String> message) {
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.message = message;
    }

    public ErrorResponseDto(List<String> message) {
        this.timestamp = System.currentTimeMillis();
        this.code = "400";
        this.message = message;
    }
}
