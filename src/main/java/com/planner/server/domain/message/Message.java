package com.planner.server.domain.message;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
    private Object data;
    private HttpStatus status;
    private String message;
    private String memo;
}
