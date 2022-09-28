package com.grootan.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    private String senderId;
    private String message;
    public String toString() {
        return '{' +
                "senderId='" + senderId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

