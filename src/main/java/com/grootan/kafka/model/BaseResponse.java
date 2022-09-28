package com.grootan.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResponse<T> {
    private String code;
    private String status;
    private String description;
    private T data;
}
