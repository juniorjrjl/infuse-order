package br.com.infuse.adapter.inbound.route.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemInsertRequest {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @Positive
    @Digits(integer = 13, fraction = 2)
    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("amount")
    private Long amount;

}
