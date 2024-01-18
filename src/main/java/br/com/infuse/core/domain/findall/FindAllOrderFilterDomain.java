package br.com.infuse.core.domain.findall;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class FindAllOrderFilterDomain {

    private final Long controlId;
    private final String productName;
    private final BigDecimal minValue;
    private final BigDecimal maxValue;
    private final Long minAmount;
    private final Long maxAmount;
    private final Long clientId;
    private final OffsetDateTime minCreatedAt;
    private final OffsetDateTime maxCreatedAt;

    public FindAllOrderFilterDomain(final Long controlId,
                                    final String productName,
                                    final BigDecimal minValue,
                                    final BigDecimal maxValue,
                                    final Long minAmount,
                                    final Long maxAmount,
                                    final Long clientId,
                                    final OffsetDateTime minCreatedAt,
                                    final OffsetDateTime maxCreatedAt) {
        this.controlId = controlId;
        this.productName = productName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.clientId = clientId;
        this.minCreatedAt = minCreatedAt;
        this.maxCreatedAt = maxCreatedAt;
    }

    public static FindAllOrderFilterDomainBuilder builder(){
        return new FindAllOrderFilterDomainBuilder();
    }

    public Long getControlId() {
        return controlId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public Long getClientId() {
        return clientId;
    }

    public OffsetDateTime getMinCreatedAt() {
        return minCreatedAt;
    }

    public OffsetDateTime getMaxCreatedAt() {
        return maxCreatedAt;
    }

    public static class FindAllOrderFilterDomainBuilder {

        private Long controlId;
        private String productName;
        private BigDecimal minValue;
        private BigDecimal maxValue;
        private Long minAmount;
        private Long maxAmount;
        private Long clientId;
        private OffsetDateTime minCreatedAt;
        private OffsetDateTime maxCreatedAt;

        public FindAllOrderFilterDomainBuilder controlId(final Long controlId) {
            this.controlId = controlId;
            return this;
        }

        public FindAllOrderFilterDomainBuilder productName(final String productName) {
            this.productName = productName;
            return this;
        }

        public FindAllOrderFilterDomainBuilder minValue(final BigDecimal minValue) {
            this.minValue = minValue;
            return this;
        }

        public FindAllOrderFilterDomainBuilder maxValue(final BigDecimal maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public FindAllOrderFilterDomainBuilder minAmount(final Long minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public FindAllOrderFilterDomainBuilder maxAmount(final Long maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public FindAllOrderFilterDomainBuilder clientId(final Long clientId) {
            this.clientId = clientId;
            return this;
        }

        public FindAllOrderFilterDomainBuilder minCreatedAt(final OffsetDateTime minCreatedAt) {
            this.minCreatedAt = minCreatedAt;
            return this;
        }

        public FindAllOrderFilterDomainBuilder maxCreatedAt(final OffsetDateTime maxCreatedAt) {
            this.maxCreatedAt = maxCreatedAt;
            return this;
        }

        public FindAllOrderFilterDomain build(){
            return new FindAllOrderFilterDomain(controlId, productName, minValue, maxValue, minAmount,
                    maxAmount, clientId, minCreatedAt, maxCreatedAt);
        }

    }

}
