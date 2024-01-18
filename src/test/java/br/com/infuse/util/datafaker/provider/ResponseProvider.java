package br.com.infuse.util.datafaker.provider;

import br.com.infuse.util.datafaker.bot.response.ErrorResponseBot;
import br.com.infuse.util.datafaker.bot.response.FieldErrorResponseBot;
import br.com.infuse.util.datafaker.bot.response.FindAllOrderResponseBot;
import br.com.infuse.util.datafaker.bot.response.OrderInsertedResponseBot;
import br.com.infuse.util.datafaker.bot.response.OrderItemInsertedResponseBot;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

public class ResponseProvider extends AbstractProvider<BaseProviders> {
    public ResponseProvider(final BaseProviders faker) {
        super(faker);
    }

    public ErrorResponseBot getErrorResponseBot(){
        return ErrorResponseBot.builder();
    }

    public FieldErrorResponseBot getFieldErrorResponseBot(){
        return FieldErrorResponseBot.builder();
    }

    public FindAllOrderResponseBot getFindAllOrderResponseBot(){
        return FindAllOrderResponseBot.builder();
    }

    public OrderInsertedResponseBot getOrderInsertedResponseBot(){
        return OrderInsertedResponseBot.builder();
    }

    public OrderItemInsertedResponseBot getOrderItemInsertedResponseBot(){
        return OrderItemInsertedResponseBot.builder();
    }

}
