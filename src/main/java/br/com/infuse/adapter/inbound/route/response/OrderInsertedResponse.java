package br.com.infuse.adapter.inbound.route.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class OrderInsertedResponse {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("controlId")
    private final long controlId;
    @JsonProperty("clientId")
    private final long clientId;
    @JsonProperty("createdAt")
    private final OffsetDateTime createdAt;
    @JsonProperty("items")
    private final List<OrderItemInsertedResponse> items;
    @JsonProperty("discount")
    private final BigDecimal discount;
    @JsonProperty("total")
    private final BigDecimal total;
    @JsonProperty("subTotal")
    private final BigDecimal subTotal;



}
