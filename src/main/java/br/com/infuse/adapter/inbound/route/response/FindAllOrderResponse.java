package br.com.infuse.adapter.inbound.route.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Builder
public class FindAllOrderResponse {

    @JsonProperty("id")
    private final Long id;
    @JsonProperty("controlId")
    private final Long controlId;
    @JsonProperty("discount")
    private final BigDecimal discount;
    @JsonProperty("subTotal")
    private final BigDecimal subTotal;
    @JsonProperty("total")
    private final BigDecimal total;
    @JsonProperty("clientId")
    private final Long clientId;
    @JsonProperty("amount")
    private final Long amount;
    @JsonProperty("createdAt")
    private final OffsetDateTime createdAt;

}
