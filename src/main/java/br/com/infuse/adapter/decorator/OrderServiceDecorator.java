package br.com.infuse.adapter.decorator;

import br.com.infuse.core.port.ClientPort;
import br.com.infuse.core.port.OrderPort;
import br.com.infuse.core.service.OrderServiceImpl;
import com.google.inject.Inject;

public class OrderServiceDecorator extends OrderServiceImpl {

    @Inject
    public OrderServiceDecorator(final OrderPort port, final ClientPort clientPort) {
        super(port, clientPort);
    }

}
