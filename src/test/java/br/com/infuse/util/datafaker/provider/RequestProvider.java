package br.com.infuse.util.datafaker.provider;

import br.com.infuse.util.datafaker.bot.request.GetAllOrderRequestBot;
import br.com.infuse.util.datafaker.bot.request.OrderInsertRequestBot;
import br.com.infuse.util.datafaker.bot.request.OrderItemInsertRequestBot;
import br.com.infuse.util.datafaker.bot.request.QueryParamsMapStubBot;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

public class RequestProvider extends AbstractProvider<BaseProviders> {

    public QueryParamsMapStubBot getQueryParamsMapStubBot(){
        return QueryParamsMapStubBot.builder();
    }

    public RequestProvider(final BaseProviders faker) {
        super(faker);
    }

    public GetAllOrderRequestBot getGetAllOrderRequestBot(){
        return GetAllOrderRequestBot.builder();
    }

    public OrderInsertRequestBot getOrderInsertRequestBot(){
        return OrderInsertRequestBot.builder();
    }

    public OrderItemInsertRequestBot getOrderItemInsertRequestBot(){
        return OrderItemInsertRequestBot.builder();
    }

}
