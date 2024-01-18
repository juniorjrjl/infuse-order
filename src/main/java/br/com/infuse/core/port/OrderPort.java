package br.com.infuse.core.port;

import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;

import java.util.List;

public interface OrderPort {

    List<OrderInsertedDomain> save(final List<OrderInsertDomain> domains);

    List<FindAllOrderDomain> findAll(final FindAllOrderFilterDomain domain);

    boolean hasControlId(final Long controlId);

}
