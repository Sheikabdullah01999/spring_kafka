package com.grootan.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grootan.kafka.kafkaserviceinterface.MessageService;
import com.grootan.kafka.model.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * provides REST API support to send  message
 * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    Logger log = LoggerFactory.getLogger(MessageController.class);
    private static final String LOG_TAG = "MESSAGE:CONTROLLER:: ";
    @Autowired
    private MessageService messageService;

    /**
     * @POST
     * This Api is used to send message to kafka
     * @param message
     * @return
     * @throws JsonProcessingException
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "send message", description = "send message")
    @PostMapping("/send")
    public ResponseEntity<BaseResponse<String>> sendMessage(@RequestParam("message") String message) throws JsonProcessingException {
        log.info(LOG_TAG + "send message");
        return messageService.sendMessage(message);
    }
}
