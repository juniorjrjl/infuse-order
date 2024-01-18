package br.com.infuse.core.service;

import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;

import java.util.List;

public interface OrderService {

    List<OrderInsertedDomain> save(final List<OrderInsertDomain> domain);

}
