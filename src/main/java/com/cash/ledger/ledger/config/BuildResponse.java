package com.cash.ledger.ledger.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BuildResponse {
    public ResponseEntity<Response> createResponse(String desc, Object data, String message, HttpStatus status) {
        Response response = new Response();
        response.setTimestamp(System.currentTimeMillis());
        response.setData(data);
        response.setMessage(message);
        response.setStatus(status);
        response.setStatusCode(status.value());
        return ResponseEntity.status(status)
                .body(response);
    }
}
