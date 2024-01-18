package br.com.infuse.adapter.mapper.repository;

import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderFilterDTO;
import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderResultDTO;
import br.com.infuse.adapter.outbound.persistence.entity.OrderEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemEntity;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertedDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface OrderMapper {

    @Mapping(target = "externalCreatedAt", source = "createdAt")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "orderItems", ignore = true)
    OrderEntity toEntity(final OrderInsertDomain domain);

    List<OrderItemEntity> toItemEntity(final List<OrderItemInsertDomain> domains);

    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "item.name", source = "name")
    @Mapping(target = "item.value", source = "value")
    OrderItemEntity toEntity(final OrderItemInsertDomain domain);

    List<OrderInsertedDomain> toDomain(final List<OrderEntity> entities);

    @Mapping(target = "createdAt", source = "externalCreatedAt")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "items", expression = "java(toItemDomain(entity.getOrderItems()))")
    OrderInsertedDomain toDomain(final OrderEntity entity);

    List<OrderItemInsertedDomain> toItemDomain(final Set<OrderItemEntity> entities);

    @Mapping(target = "id", source = "id.itemId")
    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "value", source = "item.value")
    OrderItemInsertedDomain toItemDomain(final OrderItemEntity entity);

    FindAllOrderFilterDTO toDTO(final FindAllOrderFilterDomain domain);

    List<FindAllOrderDomain> toFindAllDomain(final List<FindAllOrderResultDTO> dto);

    @Mapping(target = "createdAt", source = "externalCreatedAt")
    FindAllOrderDomain toFindAllDomain(final FindAllOrderResultDTO dto);

}
