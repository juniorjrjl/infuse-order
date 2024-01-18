package br.com.infuse.util.datafaker.provider;

import br.com.infuse.util.datafaker.bot.entity.ClientEntityBot;
import br.com.infuse.util.datafaker.bot.entity.FindAllOrderResultDTOBot;
import br.com.infuse.util.datafaker.bot.entity.ItemEntityBot;
import br.com.infuse.util.datafaker.bot.entity.OrderEntityBot;
import br.com.infuse.util.datafaker.bot.entity.OrderItemEntityBot;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

public class EntityProvider extends AbstractProvider<BaseProviders> {

    public EntityProvider(final BaseProviders faker) {
        super(faker);
    }

    public ClientEntityBot getClientEntityBot(){
        return ClientEntityBot.builder();
    }

    public OrderEntityBot getOrderEntityBot(){
        return OrderEntityBot.builder();
    }

    public OrderItemEntityBot getOrderItemEntityBot(){
        return OrderItemEntityBot.builder();
    }

    public ItemEntityBot getItemEntityBot(){
        return ItemEntityBot.builder();
    }

    public FindAllOrderResultDTOBot getFindAllOrderResultDTOBot(){
        return FindAllOrderResultDTOBot.builder();
    }

}
