package com.grootan.kafka.controller;

import com.grootan.kafka.kafkaserviceinterface.ConsumerService;
import com.grootan.kafka.model.BaseResponse;
import com.grootan.kafka.model.ConsumeMessage;
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
 * provides REST API support to get message from Data base
 * @author <a href="sheikabdullah.m@grootan.com">sheik abdullah</a>
 */
@RestController
@RequestMapping("/messages")
public class ConsumerController {
    Logger log = LoggerFactory.getLogger(ConsumerController.class);
    private static final String LOG_TAG = "CONSUMER:CONTROLLER:: ";
    @Autowired
    ConsumerService consumerService;

    /**
     * GET
     * This Api is used to get message using messageId from the data base
     * @param message
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "get message", description = "get message by id")
    @GetMapping
    public ResponseEntity<BaseResponse<ConsumeMessage>> getMessage(@RequestParam("messageId") String message) {
        log.info(LOG_TAG + "get message");
        return consumerService.getMessage(message);
    }
}
