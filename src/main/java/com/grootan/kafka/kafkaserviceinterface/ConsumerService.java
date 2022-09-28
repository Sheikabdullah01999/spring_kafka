package com.grootan.kafka.kafkaserviceinterface;

import com.grootan.kafka.model.BaseResponse;
import com.grootan.kafka.model.ConsumeMessage;
import org.springframework.http.ResponseEntity;

/**
 *  @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
public interface ConsumerService {

     public ResponseEntity<BaseResponse<ConsumeMessage>> getMessage(String messageId);
}
