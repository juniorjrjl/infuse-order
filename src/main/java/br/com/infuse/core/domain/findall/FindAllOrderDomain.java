package br.com.infuse.core.domain.findall;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class FindAllOrderDomain {

    private final Long id;
    private final Long controlId;
    private final BigDecimal discount;
    private final BigDecimal subTotal;
    private final BigDecimal total;
    private final Long clientId;
    private final OffsetDateTime createdAt;
    private final Long amount;

    public static FindAllOrderDomainBuilder builder(){
        return new FindAllOrderDomainBuilder();
    }

    public FindAllOrderDomain(final Long id,
                              final Long controlId,
                              final BigDecimal discount,
                              final BigDecimal subTotal,
                              final BigDecimal total,
                              final Long clientId,
                              final OffsetDateTime createdAt,
                              final Long amount) {
        this.id = id;
        this.controlId = controlId;
        this.discount = discount;
        this.subTotal = subTotal;
        this.total = total;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getControlId() {
        return controlId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Long getClientId() {
        return clientId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getAmount() {
        return amount;
    }


    public static final class FindAllOrderDomainBuilder {
        private Long id;
        private Long controlId;
        private BigDecimal discount;
        private BigDecimal subTotal;
        private BigDecimal total;
        private Long clientId;
        private OffsetDateTime createdAt;
        private Long amount;

        public FindAllOrderDomainBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FindAllOrderDomainBuilder controlId(Long controlId) {
            this.controlId = controlId;
            return this;
        }

        public FindAllOrderDomainBuilder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public FindAllOrderDomainBuilder subTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public FindAllOrderDomainBuilder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public FindAllOrderDomainBuilder clientId(Long clientId) {
            this.clientId = clientId;
            return this;
        }

        public FindAllOrderDomainBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FindAllOrderDomainBuilder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        public FindAllOrderDomain build() {
            return new FindAllOrderDomain(id, controlId, discount, subTotal, total, clientId, createdAt, amount);
        }
    }
}
