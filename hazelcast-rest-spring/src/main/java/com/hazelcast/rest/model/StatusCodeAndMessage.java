package com.hazelcast.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCodeAndMessage {
    @JsonProperty("statusCode")
    private final int statusCode;
    @JsonProperty("message")
    private final String message;

    public StatusCodeAndMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

/*    public static class StatusCodeAndMessageBuilder {
        private int statusCode;
        private String message;
        public StatusCodeAndMessageBuilder() {
        }

        public StatusCodeAndMessageBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }
        public StatusCodeAndMessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public StatusCodeAndMessage build() {
            return new StatusCodeAndMessage(this.statusCode, this.message);
        }
    }*/
}
