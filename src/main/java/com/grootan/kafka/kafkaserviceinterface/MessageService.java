package com.grootan.kafka.kafkaserviceinterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grootan.kafka.model.BaseResponse;
import org.springframework.http.ResponseEntity;

/**
 *  @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
public interface MessageService {
    ResponseEntity<BaseResponse<String>> sendMessage(String message) throws JsonProcessingException;
}
