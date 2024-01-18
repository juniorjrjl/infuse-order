package br.com.infuse.util.datafaker.bot.entity;

import br.com.infuse.adapter.outbound.persistence.entity.ClientEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemEntity;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class OrderEntityBot extends AbstractBot<OrderEntity> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<BigDecimal> discount = () -> faker.monetary().random();
    private Supplier<BigDecimal> subTotal = () -> faker.monetary().random();
    private Supplier<BigDecimal> total = () -> faker.monetary().random();
    private Supplier<ClientEntity> client = () -> faker.entity().getClientEntityBot().build();
    private Supplier<Set<OrderItemEntity>> orderItems = () -> new HashSet<>(faker.entity()
            .getOrderItemEntityBot()
            .withOrder(OrderEntity::new)
            .build(faker.number().randomDigitNotZero()));
    private Supplier<OffsetDateTime> externalCreatedAt = () -> faker.offsetDateTime().birthday();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();
    private Supplier<OffsetDateTime> updatedAt = () -> faker.offsetDateTime().birthday();

    public static OrderEntityBot builder(){
        return new OrderEntityBot();
    }

    @Override
    public OrderEntity build() {
        OrderEntity order = new OrderEntity();
        order.setId(id.get());
        order.setControlId(controlId.get());
        order.setDiscount(discount.get());
        order.setSubTotal(subTotal.get());
        order.setTotal(total.get());
        order.setClient(client.get());
        order.setOrderItems(orderItems.get());
        order.setExternalCreatedAt(externalCreatedAt.get());
        order.setCreatedAt(createdAt.get());
        order.setUpdatedAt(updatedAt.get());
        return order;
    }
}
