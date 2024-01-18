package br.com.infuse.core.service;

import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.port.OrderPort;

import java.util.List;

public class OrderQueryServiceImpl implements OrderQueryService{

    private final OrderPort orderPort;

    public OrderQueryServiceImpl(final OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @Override
    public List<FindAllOrderDomain> findAll(final FindAllOrderFilterDomain domain) {
        return orderPort.findAll(domain);
    }
}
