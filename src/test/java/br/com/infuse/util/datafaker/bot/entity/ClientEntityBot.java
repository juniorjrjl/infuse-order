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
import java.util.Set;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class ClientEntityBot extends AbstractBot<ClientEntity> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<String> name = () -> faker.name().name();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();
    private Supplier<OffsetDateTime> updatedAt = () -> faker.offsetDateTime().birthday();

    public static ClientEntityBot builder(){
        return new ClientEntityBot();
    }

    @Override
    public ClientEntity build() {
        ClientEntity client = new ClientEntity();
        client.setId(id.get());
        client.setName(name.get());
        client.setCreatedAt(createdAt.get());
        client.setUpdatedAt(updatedAt.get());
        return client;
    }
}
