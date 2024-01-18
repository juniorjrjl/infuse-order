package br.com.infuse.adapter.inbound.route.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemInsertedResponse {

    @JsonProperty("id")
    private final Long id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("value")
    private final BigDecimal value;
    @JsonProperty("amount")
    private final Long amount;

}
