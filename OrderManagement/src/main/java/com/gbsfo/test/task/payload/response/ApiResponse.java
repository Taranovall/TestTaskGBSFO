package com.gbsfo.test.task.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.gbsfo.test.task.util.Constant.DATE_TIME_PATTERN;


@Getter
@Setter
public class ApiResponse {

    private String timestamp;
    private HttpStatus status;
    private String message;

    public ApiResponse(HttpStatus status, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        this.timestamp = LocalDateTime.now().format(formatter);
        this.status = status;
        this.message = message;
    }
}
