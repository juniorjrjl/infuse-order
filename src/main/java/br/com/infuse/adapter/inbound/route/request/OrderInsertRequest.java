package br.com.infuse.adapter.inbound.route.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInsertRequest {

    @JsonProperty("controlId")
    @NotNull
    @Positive
    private Long controlId;
    @JsonProperty("clientId")
    @NotNull
    @Positive
    private Long clientId;
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    @NotNull
    @Valid
    @JsonProperty("items")
    private List<OrderItemInsertRequest> items;

}
