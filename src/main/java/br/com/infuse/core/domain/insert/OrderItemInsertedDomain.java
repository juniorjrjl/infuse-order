package br.com.infuse.core.domain.insert;

import java.math.BigDecimal;

public class OrderItemInsertedDomain {

    private final Long id;
    private final String name;

    private final BigDecimal value;

    private final Long amount;

    public static OrderItemInsertDomainBuilder builder(){
        return new OrderItemInsertDomainBuilder();
    }

    public OrderItemInsertedDomain(final Long id,
                                   final String name,
                                   final BigDecimal value,
                                   final Long amount) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Long getAmount() {
        return amount;
    }


    public static final class OrderItemInsertDomainBuilder {
        private Long id;
        private String name;
        private BigDecimal value;
        private Long amount;

        public OrderItemInsertDomainBuilder id(final Long id){
            this.id = id;
            return this;
        }

        public OrderItemInsertDomainBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public OrderItemInsertDomainBuilder value(final BigDecimal value) {
            this.value = value;
            return this;
        }

        public OrderItemInsertDomainBuilder amount(final Long amount) {
            this.amount = amount;
            return this;
        }

        public OrderItemInsertedDomain build() {
            return new OrderItemInsertedDomain(id, name, value, amount);
        }
    }
}
