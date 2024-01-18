package br.com.infuse.adapter.outbound.persistence.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Builder
public class FindAllOrderFilterDTO {

    private final Long controlId;
    private final String productName;
    private final BigDecimal minValue;
    private final BigDecimal maxValue;
    private final Long minAmount;
    private final Long maxAmount;
    private final Long clientId;
    private final OffsetDateTime minCreatedAt;
    private final OffsetDateTime maxCreatedAt;

    public boolean productNameIsNotBlank(){
        return productName != null && !productName.trim().isEmpty();
    }

    public boolean hasSomeFilter(){
        return controlId != null ||
                productNameIsNotBlank() ||
                useMinOrMaxValueFilter() ||
                useMinOrMaxAmountFilter()||
                clientId != null ||
                useMinOrMaxCreatedAtFilter();
    }

    public String getLikeName(){
        return String.format("%%%s%%", this.productName);
    }

    public boolean useMinOrMaxValueFilter(){
        return minValue != null || maxValue != null;
    }

    public boolean useMinAndMaxValueFilter(){
        return minValue != null && maxValue != null;
    }

    public boolean useMinOrMaxAmountFilter(){
        return minAmount != null || maxAmount != null;
    }

    public boolean useMinAndMaxAmountFilter(){
        return minAmount != null && maxAmount != null;
    }

    public boolean useMinOrMaxCreatedAtFilter(){
        return minCreatedAt != null || maxCreatedAt != null;
    }

    public boolean useMinAndMaxCreatedAtFilter(){
        return minCreatedAt != null && maxCreatedAt != null;
    }

}
