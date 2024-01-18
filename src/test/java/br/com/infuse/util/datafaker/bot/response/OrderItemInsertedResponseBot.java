package br.com.infuse.util.datafaker.bot.response;

import br.com.infuse.adapter.inbound.route.response.OrderItemInsertedResponse;
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
public class OrderItemInsertedResponseBot extends AbstractBot<OrderItemInsertedResponse> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<String> name = () -> faker.name().name();
    private Supplier<BigDecimal> value = () -> faker.monetary().random();
    private Supplier<Long> amount = () -> faker.number().randomNumber();

    public static OrderItemInsertedResponseBot builder(){
        return new OrderItemInsertedResponseBot();
    }

    @Override
    public OrderItemInsertedResponse build() {
        return OrderItemInsertedResponse.builder()
                .id(id.get())
                .name(name.get())
                .value(value.get())
                .amount(amount.get())
                .build();
    }
}
