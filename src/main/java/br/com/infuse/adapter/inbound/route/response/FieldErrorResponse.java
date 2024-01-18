package br.com.infuse.adapter.inbound.route.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldErrorResponse {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("message")
    private final String message;

}
