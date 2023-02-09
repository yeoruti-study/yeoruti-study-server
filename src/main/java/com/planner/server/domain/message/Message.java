package com.planner.server.domain.message;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Object data;
    private HttpStatus status;
    private String message;
    private String memo;
}
