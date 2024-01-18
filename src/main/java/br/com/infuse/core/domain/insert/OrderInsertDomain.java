package br.com.infuse.core.domain.insert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class OrderInsertDomain {

    private final Long controlId;

    private final Long clientId;

    private final OffsetDateTime createdAt;

    private final BigDecimal discount;

    private final BigDecimal subTotal;

    private final BigDecimal total;

    private final List<OrderItemInsertDomain> items;

    public static OrderInsertDomainBuilder builder(){
        return new OrderInsertDomainBuilder();
    }

    public OrderInsertDomain(final Long controlId,
                             final Long clientId,
                             final OffsetDateTime createdAt,
                             final BigDecimal discount,
                             final BigDecimal subTotal,
                             final BigDecimal total,
                             final List<OrderItemInsertDomain> items) {
        this.controlId = controlId;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.discount = discount;
        this.subTotal = subTotal;
        this.total = total;
        this.items = items;
    }

    public Long getControlId() {
        return controlId;
    }

    public Long getClientId() {
        return clientId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
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

    public List<OrderItemInsertDomain> getItems() {
        return items;
    }

    public static final class OrderInsertDomainBuilder {
        private Long controlId;
        private Long clientId;
        private OffsetDateTime createdAt;
        private BigDecimal discount;

        private BigDecimal subTotal;

        private BigDecimal total;
        private List<OrderItemInsertDomain> items;

        public OrderInsertDomainBuilder controlId(final Long controlId) {
            this.controlId = controlId;
            return this;
        }

        public OrderInsertDomainBuilder clientId(final Long clientId) {
            this.clientId = clientId;
            return this;
        }

        public OrderInsertDomainBuilder createdAt(final OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderInsertDomainBuilder discount(final BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public OrderInsertDomainBuilder subTotal(final BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public OrderInsertDomainBuilder total(final BigDecimal total) {
            this.total = total;
            return this;
        }

        public OrderInsertDomainBuilder items(final List<OrderItemInsertDomain> items) {
            this.items = items;
            return this;
        }

        public OrderInsertDomain build() {
            return new OrderInsertDomain(controlId, clientId, createdAt, discount, subTotal, total,items);
        }
    }
}
