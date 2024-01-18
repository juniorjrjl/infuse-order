package br.com.infuse.util.datafaker.bot.entity;

import br.com.infuse.adapter.outbound.persistence.entity.ItemEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemId;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class OrderItemEntityBot extends AbstractBot<OrderItemEntity> {

    private Supplier<ItemEntity> item = () -> faker.entity().getItemEntityBot().build();
    private Supplier<OrderEntity> order = () -> faker.entity().getOrderEntityBot().build();
    private Supplier<Long> amount = () -> faker.number().randomNumber();

    public static OrderItemEntityBot builder(){
        return new OrderItemEntityBot();
    }

    @Override
    public OrderItemEntity build() {
        OrderItemEntity orderItem = new OrderItemEntity();
        OrderEntity orderEntity = order.get();
        ItemEntity itemEntity = item.get();
        orderItem.setId(new OrderItemId(orderEntity.getId(), itemEntity.getId()));
        orderItem.setOrder(orderEntity);
        orderItem.setItem(itemEntity);
        orderItem.setAmount(amount.get());
        return orderItem;
    }
}
