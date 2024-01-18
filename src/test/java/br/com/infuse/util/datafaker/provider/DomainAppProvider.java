package br.com.infuse.util.datafaker.provider;

import br.com.infuse.util.datafaker.bot.domain.FindAllOrderDomainBot;
import br.com.infuse.util.datafaker.bot.domain.FindAllOrderFilterDomainBot;
import br.com.infuse.util.datafaker.bot.domain.OrderInsertDomainBot;
import br.com.infuse.util.datafaker.bot.domain.OrderInsertedDomainBot;
import br.com.infuse.util.datafaker.bot.domain.OrderItemInsertDomainBot;
import br.com.infuse.util.datafaker.bot.domain.OrderItemInsertedDomainBot;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

public class DomainAppProvider extends AbstractProvider<BaseProviders> {

    public DomainAppProvider(final BaseProviders faker) {
        super(faker);
    }

    public FindAllOrderFilterDomainBot getFindAllOrderFilterDomainBot(){
        return FindAllOrderFilterDomainBot.builder();
    }

    public FindAllOrderDomainBot getFindAllOrderDomainBot(){
        return FindAllOrderDomainBot.builder();
    }

    public OrderInsertDomainBot getOrderInsertDomainBot(){
        return OrderInsertDomainBot.builder();
    }

    public OrderInsertedDomainBot getOrderInsertedDomainBot(){
        return OrderInsertedDomainBot.builder();
    }

    public OrderItemInsertDomainBot getOrderItemInsertDomainBot(){
        return OrderItemInsertDomainBot.builder();
    }

    public OrderItemInsertedDomainBot getOrderItemInsertedDomainBot(){
        return OrderItemInsertedDomainBot.builder();
    }

}
