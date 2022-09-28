package com.grootan.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.grootan.kafka.kafkaserviceinterface.MessageService;
import com.grootan.kafka.model.BaseResponse;
import com.grootan.kafka.model.MessageResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.grootan.kafka.model.Constant.*;

/**
 * Message service: resource for message Post  Rest Api
 * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
@Service
public class MessageServiceImplementation implements MessageService {
    Logger log = LoggerFactory.getLogger(MessageServiceImplementation.class);
    private static final String LOG_TAG = "MESSAGE:SERVICE:: ";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * send message using kafka template
     * @param message
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public ResponseEntity<BaseResponse<String>> sendMessage(String message) throws JsonProcessingException {
        if (StringUtils.isBlank(message)) {
            log.info(LOG_TAG + "field should not empty");
            return new ResponseEntity<>(new BaseResponse<>(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase(), EMPTY_FIELDS_MESSAGE, null), HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
        }
        String senderId=UUID.randomUUID().toString();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String produceMessage = objectWriter.writeValueAsString(new MessageResponse(senderId, message));
        try{
            kafkaTemplate.send(PRODUCER_KAFKA_TOPIC, produceMessage);
        }catch (KafkaException e)
        {
            log.info(LOG_TAG + e.getMessage());
            return new ResponseEntity<>(new BaseResponse<>(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), MESSAGE_SEND_FAILED_MESSAGE, senderId), HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
        log.info(LOG_TAG + MESSAGE_SEND_SUCCESS_MESSAGE);
        return new ResponseEntity<>(new BaseResponse<>(String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase(), MESSAGE_SEND_SUCCESS_MESSAGE, senderId), HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
