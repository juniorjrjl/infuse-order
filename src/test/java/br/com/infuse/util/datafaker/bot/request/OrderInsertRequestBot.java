package br.com.infuse.util.datafaker.bot.request;

import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.adapter.inbound.route.request.OrderItemInsertRequest;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class OrderInsertRequestBot extends AbstractBot<OrderInsertRequest> {

    private Supplier<Long> controlId = () -> faker.number().numberBetween(0L,  Long.MAX_VALUE);
    private Supplier<Long> clientId = () -> faker.number().numberBetween(0L,  Long.MAX_VALUE);
    private Supplier<OffsetDateTime> createAt = () -> faker.offsetDateTime().birthday();
    private Supplier<List<OrderItemInsertRequest>> items = () -> faker.request()
            .getOrderItemInsertRequestBot()
            .build(faker.number().randomDigitNotZero());


    public static OrderInsertRequestBot builder(){
        return new OrderInsertRequestBot();
    }

    @Override
    public OrderInsertRequest build() {
        return OrderInsertRequest.builder()
                .controlId(controlId.get())
                .clientId(clientId.get())
                .createdAt(createAt.get())
                .items(items.get())
                .build();
    }
}
