package br.com.infuse.util.datafaker.bot.request;

import br.com.infuse.adapter.inbound.route.request.OrderItemInsertRequest;
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
public class OrderItemInsertRequestBot extends AbstractBot<OrderItemInsertRequest> {

    private Supplier<String> name = () -> faker.name().name();
    private Supplier<BigDecimal> value = () -> faker.monetary().random();
    private Supplier<Long> amount = () -> faker.number().numberBetween(1L, 10L);

    public static OrderItemInsertRequestBot builder(){
        return new OrderItemInsertRequestBot();
    }

    @Override
    public OrderItemInsertRequest build() {
        return OrderItemInsertRequest.builder()
                .name(name.get())
                .value(value.get())
                .amount(amount.get())
                .build();
    }
}
