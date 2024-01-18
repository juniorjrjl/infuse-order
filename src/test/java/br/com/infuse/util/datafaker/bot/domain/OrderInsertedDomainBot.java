package br.com.infuse.util.datafaker.bot.domain;

import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertedDomain;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class OrderInsertedDomainBot extends AbstractBot<OrderInsertedDomain> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();;
    private Supplier<BigDecimal> discount = () -> faker.monetary().random();
    private Supplier<BigDecimal> subTotal = () -> faker.monetary().random();
    private Supplier<BigDecimal> total = () -> faker.monetary().random();
    private Supplier<List<OrderItemInsertedDomain>> items = () -> faker.domainApp()
            .getOrderItemInsertedDomainBot().build(faker.number().randomDigitNotZero());

    public static OrderInsertedDomainBot builder(){
        return new OrderInsertedDomainBot();
    }

    @Override
    public OrderInsertedDomain build() {
        return OrderInsertedDomain.builder()
                .id(id.get())
                .controlId(controlId.get())
                .clientId(clientId.get())
                .createdAt(createdAt.get())
                .discount(discount.get())
                .subTotal(subTotal.get())
                .total(total.get())
                .items((items.get()))
                .build();
    }

}
