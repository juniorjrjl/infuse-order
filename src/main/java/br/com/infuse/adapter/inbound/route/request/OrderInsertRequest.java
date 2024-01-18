package br.com.infuse.adapter.inbound.route.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInsertRequest {

    @JsonProperty("controlId")
    @NotNull(message = "informe um número de controle válido")
    @Positive(message = "informe um número de controle válido")
    private Long controlId;
    @JsonProperty("clientId")
    @NotNull(message = "informe um identificador de cliente válidio válido")
    @Positive(message = "informe um identificador de cliente válidio válido")
    private Long clientId;
    @JsonProperty("createdAt")
    private OffsetDateTime createdAt;
    @NotNull(message = "O pedido deve ter pelo menos 1 item")
    @Valid
    @JsonProperty("items")
    @Size(min = 1, message = "O pedido deve ter pelo menos 1 item")
    private List<OrderItemInsertRequest> items;

}
