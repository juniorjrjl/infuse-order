package br.com.infuse.core.domain.findall;

import java.math.BigDecimal;

public class FindAllOrderItemDomain {

    private final Long id;

    private final String name;

    private final BigDecimal value;

    private final BigDecimal amount;

    public static FindAllOrderItemDomainBuilder builder(){
        return new FindAllOrderItemDomainBuilder();
    }

    public FindAllOrderItemDomain(final Long id,
                                  final String name,
                                  final BigDecimal value,
                                  final BigDecimal amount) {
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

    public BigDecimal getAmount() {
        return amount;
    }


    public static final class FindAllOrderItemDomainBuilder {
        private Long id;
        private String name;
        private BigDecimal value;
        private BigDecimal amount;

        public FindAllOrderItemDomainBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public FindAllOrderItemDomainBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public FindAllOrderItemDomainBuilder value(final BigDecimal value) {
            this.value = value;
            return this;
        }

        public FindAllOrderItemDomainBuilder amount(final BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public FindAllOrderItemDomain build() {
            return new FindAllOrderItemDomain(id, name, value, amount);
        }
    }
}
