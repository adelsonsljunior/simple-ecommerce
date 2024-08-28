package com.adelsonsljunior.simpleecommerce.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomResponse {

    @JsonProperty("message")
    private String message;

    public CustomResponse(String message) {
        this.message = message;
    }
}
