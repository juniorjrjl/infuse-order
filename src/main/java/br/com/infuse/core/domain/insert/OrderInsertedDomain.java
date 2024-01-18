package br.com.infuse.core.domain.insert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class OrderInsertedDomain {

    private final Long id;
    private final Long controlId;

    private final Long clientId;

    private final OffsetDateTime createdAt;

    private final List<OrderItemInsertedDomain> items;

    private final BigDecimal discount;

    private final BigDecimal total;

    private final BigDecimal subTotal;

    public static OrderInsertedDomainBuilder builder(){
        return new OrderInsertedDomainBuilder();
    }

    public OrderInsertedDomain(final Long id,
                               final Long controlId,
                               final Long clientId,
                               final OffsetDateTime createdAt,
                               final List<OrderItemInsertedDomain> items,
                               final BigDecimal discount,
                               final BigDecimal total,
                               final BigDecimal subTotal) {
        this.id = id;
        this.controlId = controlId;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.items = items;
        this.discount = discount;
        this.total = total;
        this.subTotal = subTotal;
    }

    public Long getId() {
        return id;
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

    public List<OrderItemInsertedDomain> getItems() {
        return items;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }


    public static final class OrderInsertedDomainBuilder {
        private Long id;
        private Long controlId;
        private Long clientId;
        private OffsetDateTime createdAt;
        private List<OrderItemInsertedDomain> items;
        private BigDecimal discount;
        private BigDecimal total;
        private BigDecimal subTotal;

        private OrderInsertedDomainBuilder() {
        }

        public static OrderInsertedDomainBuilder anOrderInsertedDomain() {
            return new OrderInsertedDomainBuilder();
        }

        public OrderInsertedDomainBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public OrderInsertedDomainBuilder controlId(final Long controlId) {
            this.controlId = controlId;
            return this;
        }

        public OrderInsertedDomainBuilder clientId(final Long clientId) {
            this.clientId = clientId;
            return this;
        }

        public OrderInsertedDomainBuilder createdAt(final OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderInsertedDomainBuilder items(final List<OrderItemInsertedDomain> items) {
            this.items = items;
            return this;
        }

        public OrderInsertedDomainBuilder discount(final BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public OrderInsertedDomainBuilder total(final BigDecimal total) {
            this.total = total;
            return this;
        }

        public OrderInsertedDomainBuilder subTotal(final BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public OrderInsertedDomain build() {
            return new OrderInsertedDomain(id, controlId, clientId, createdAt, items, discount, total, subTotal);
        }
    }
}
