package com.grootan.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grootan.kafka.kafkaserviceinterface.ConsumerService;
import com.grootan.kafka.model.BaseResponse;
import com.grootan.kafka.model.ConsumeMessage;
import com.grootan.kafka.model.MessageResponse;
import com.grootan.kafka.repository.ConsumerRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.grootan.kafka.model.Constant.*;

/**
 * it provides support for kafka listener(CONSUMER) &
 * data support for controller
 * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */

@Service
public class ConsumerServiceImplementation implements ConsumerService {

    Logger log = LoggerFactory.getLogger(ConsumerServiceImplementation.class);
    private static final String LOG_TAG = "CONSUMER:SERVICE:: ";


    @Autowired
    private ConsumerRepository consumerRepository;
    /**
     * it consume message
     * using kafka listener, this method will consume message (based on Topics)
     * @param message
     */
    @KafkaListener(topics = CONSUMER_KAFKA_TOPIC, groupId = CONSUMER_GROUP_ID)
    public void consumeMessage(String message) {
        log.info(LOG_TAG + "consumeMessage");
        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageResponse messageResponse = mapper.readValue(message, MessageResponse.class);
            log.info(LOG_TAG + "message :" + messageResponse);
            saveMessage(messageResponse);
        } catch (IOException e) {
            log.error(LOG_TAG + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * To sava message using JpaRepo
     * @param message
     */
    public void saveMessage(MessageResponse message) {
        log.info(LOG_TAG + "save method");
        ConsumeMessage consumedMessage = new ConsumeMessage(message.getSenderId(), message.getMessage());
        consumerRepository.save(consumedMessage);
        log.info(LOG_TAG + "message saved successfully ");
    }

    /**
     * To get message based on messageId
     * @param messageId
     * @return
     */
    @Override
    public ResponseEntity<BaseResponse<ConsumeMessage>> getMessage(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            log.error(LOG_TAG + "Field should not empty");
            return new ResponseEntity<>(new BaseResponse<>(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase(), EMPTY_FIELDS_MESSAGE, null), HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
        }
        ConsumeMessage consumeMessage = consumerRepository.getByMessageId(messageId);
        if (consumeMessage == null) {
            log.info(LOG_TAG + "Message not found");
            return new ResponseEntity<>(new BaseResponse<>(String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase(), MESSAGE_NOT_FOUND_RESPONSE, null), HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        return new ResponseEntity<>(new BaseResponse<>(String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase(), MESSAGE_AVAILABLE_RESPONSE, consumeMessage), HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
