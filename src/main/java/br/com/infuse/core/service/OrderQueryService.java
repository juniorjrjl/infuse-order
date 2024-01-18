package br.com.infuse.core.service;

import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;

import java.util.List;

public interface OrderQueryService {

    List<FindAllOrderDomain> findAll(final FindAllOrderFilterDomain domain);

}
