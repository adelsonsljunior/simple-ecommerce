package com.adelsonsljunior.simpleecommerce.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BadRequestResponse extends CustomResponse {

    @JsonProperty("errors")
    private Map<String, String> errors;

    public BadRequestResponse(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
