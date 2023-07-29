package com.universal.em.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseDto<T> implements ResponseDto<T> {

    private int status;
    private String message = null;
    private T data;
    private LocalDateTime timestamp;
    private String requestId;
    private Long numItems;

    public SuccessResponseDto(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public SuccessResponseDto(T data) {
        this.data = data;
        this.status = HttpStatus.OK.value();
        this.message = "Success";
        this.timestamp = LocalDateTime.now();
    }

    public SuccessResponseDto(String message, String requestId) {
        this.message = message;
        this.status = HttpStatus.OK.value();
        this.requestId = requestId;
        this.timestamp = LocalDateTime.now();

    }

    public SuccessResponseDto(T data, Long requestId) {
        this.data = data;
        this.requestId = String.valueOf(requestId);
        this.message = "Success";
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.OK.value();
    }

    public SuccessResponseDto(T data, String message, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
    }

    public SuccessResponseDto(T data, String message, Long numItems) {
        this.data = data;
        this.message = message;
        this.numItems = numItems;
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.OK.value();
    }

    public SuccessResponseDto(T data, String message, String requestId) {
        this.data = data;
        this.message = message;
        this.requestId = requestId;
        this.timestamp = LocalDateTime.now();
        this.status = HttpStatus.OK.value();
    }

    public SuccessResponseDto(T data, String message, Long numItems, String requestId) {
        this.data = data;
        this.message = message;
        this.numItems = numItems;
        this.requestId = requestId;
        this.timestamp = LocalDateTime.now();
    }

    public SuccessResponseDto(T data, String message, HttpStatus status, Long numItems, String requestId) {
        this.data = data;
        this.message = message;
        this.numItems = numItems;
        this.status = status.value();
        this.requestId = requestId;
        this.timestamp = LocalDateTime.now();
    }
}
