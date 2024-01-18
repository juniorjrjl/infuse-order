package br.com.infuse.core.domain.insert;

import org.hibernate.validator.constraints.Currency;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class OrderItemInsertDomain {

    private final String name;
    private final BigDecimal value;
    private final Long amount;

    public static OrderItemInsertDomainBuilder builder(){
        return new OrderItemInsertDomainBuilder();
    }

    public OrderItemInsertDomain(final String name,
                                 final BigDecimal value,
                                 final Long amount) {
        this.name = name;
        this.value = value;
        this.amount = amount;
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
        private String name;
        private BigDecimal value;
        private Long amount;

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

        public OrderItemInsertDomain build() {
            return new OrderItemInsertDomain(name, value, amount);
        }
    }
}
