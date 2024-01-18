package br.com.infuse.adapter.inbound.route.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Builder
public class ErrorResponse {

    @JsonProperty("status")
    private final int status;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("details")
    private final String details;

    @Singular
    @JsonProperty("fields")
    @JsonInclude(NON_EMPTY)
    private final List<FieldErrorResponse> fields;

}
