package br.com.infuse.util.datafaker.bot.entity;

import br.com.infuse.adapter.outbound.persistence.entity.ItemEntity;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class ItemEntityBot extends AbstractBot<ItemEntity> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<String> name = () -> faker.name().name();
    private Supplier<BigDecimal> value = () -> faker.monetary().random();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();
    private Supplier<OffsetDateTime> updatedAt = () -> faker.offsetDateTime().birthday();

    public static ItemEntityBot builder(){
        return new ItemEntityBot();
    }

    @Override
    public ItemEntity build() {
        ItemEntity item = new ItemEntity();
        item.setId(id.get());
        item.setName(name.get());
        item.setValue(value.get());
        item.setCreatedAt(createdAt.get());
        item.setUpdatedAt(updatedAt.get());
        return item;
    }
}
