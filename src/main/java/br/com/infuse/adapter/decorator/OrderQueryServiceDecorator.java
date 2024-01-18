package br.com.infuse.adapter.decorator;

import br.com.infuse.core.port.OrderPort;
import br.com.infuse.core.service.OrderQueryServiceImpl;
import com.google.inject.Inject;

public class OrderQueryServiceDecorator extends OrderQueryServiceImpl {

    @Inject
    public OrderQueryServiceDecorator(final OrderPort orderPort) {
        super(orderPort);
    }

}
