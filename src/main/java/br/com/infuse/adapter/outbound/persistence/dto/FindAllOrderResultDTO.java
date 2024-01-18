package br.com.infuse.adapter.outbound.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindAllOrderResultDTO {

    private Long id;
    private Long controlId;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal total;
    private Long clientId;
    private OffsetDateTime externalCreatedAt;
    private Long amount;

}
