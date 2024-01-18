package br.com.infuse.util.datafaker.bot.domain;

import br.com.infuse.core.domain.insert.OrderItemInsertDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertedDomain;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class OrderItemInsertedDomainBot extends AbstractBot<OrderItemInsertedDomain> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<String> name = () -> faker.name().name();
    private Supplier<BigDecimal> value = () -> faker.monetary().random();
    private Supplier<Long> amount = () -> faker.number().randomNumber();

    public static OrderItemInsertedDomainBot builder(){
        return new OrderItemInsertedDomainBot();
    }

    @Override
    public OrderItemInsertedDomain build() {
        return OrderItemInsertedDomain.builder()
                .id(id.get())
                .name(name.get())
                .value(value.get())
                .amount(amount.get())
                .build();
    }
}
