package br.com.infuse.util.datafaker.bot.response;

import br.com.infuse.adapter.inbound.route.response.OrderInsertedResponse;
import br.com.infuse.adapter.inbound.route.response.OrderItemInsertedResponse;
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
public class OrderInsertedResponseBot extends AbstractBot<OrderInsertedResponse> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();;
    private Supplier<BigDecimal> discount = () -> faker.monetary().random();
    private Supplier<BigDecimal> subTotal = () -> faker.monetary().random();
    private Supplier<BigDecimal> total = () -> faker.monetary().random();
    private Supplier<List<OrderItemInsertedResponse>> items = () -> faker.response()
            .getOrderItemInsertedResponseBot().build(faker.number().randomDigitNotZero());

    public static OrderInsertedResponseBot builder(){
        return new OrderInsertedResponseBot();
    }

    @Override
    public OrderInsertedResponse build() {
        return OrderInsertedResponse.builder()
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
