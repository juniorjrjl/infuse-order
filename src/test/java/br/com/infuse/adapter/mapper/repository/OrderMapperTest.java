package br.com.infuse.adapter.mapper.repository;

import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderFilterDTO;
import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderResultDTO;
import br.com.infuse.adapter.outbound.persistence.entity.ItemEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemEntity;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertedDomain;
import br.com.infuse.util.datafaker.CustomFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private static final CustomFaker faker = CustomFaker.getInstance();

    private OrderMapper orderMapper;

    @BeforeEach
    void setup(){
        orderMapper = new OrderMapperImpl();
    }

    @Test
    void whenSendInsertDomain_thenReturnEntity(){
        OrderInsertDomain domain = faker.domainApp().getOrderInsertDomainBot().build();
        OrderEntity actual = orderMapper.toEntity(domain);
        List<String> fieldsToIgnore = Arrays.asList("client", "orderItems", "createdAt",
                "updatedAt", "externalCreatedAt", "total", "subtotal", "discount", "items", "id");

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore.toArray(new String[0]))
                .isEqualTo(domain);
        assertThat(actual.getClient().getId()).isEqualTo(domain.getClientId());
        assertThat(actual.getOrderItems()).isNullOrEmpty();
        assertThat(actual.getExternalCreatedAt()).isEqualTo(domain.getCreatedAt());
        assertThat(actual.getCreatedAt()).isNull();
        assertThat(actual.getUpdatedAt()).isNull();
        assertThat(actual.getTotal()).usingComparator(BigDecimal::compareTo).isEqualTo(domain.getTotal());
        assertThat(actual.getSubTotal()).usingComparator(BigDecimal::compareTo).isEqualTo(domain.getSubTotal());
        assertThat(actual.getDiscount()).usingComparator(BigDecimal::compareTo).isEqualTo(domain.getDiscount());
    }

    @Test
    void whenSendItemDomain_ThenReturnOrderItemEntity(){
        List<OrderItemInsertDomain> domains = faker.domainApp().getOrderItemInsertDomainBot().build(1);
        List<OrderItemEntity> actual = orderMapper.toItemEntity(domains);

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(domains.size());
        OrderItemInsertDomain domain = domains.get(0);
        OrderItemEntity entity = actual.get(0);
        ItemEntity itemEntity = entity.getItem();
        assertThat(entity.getAmount()).isEqualTo(domain.getAmount());
        assertThat(itemEntity).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "amount")
                .isEqualTo(domain);
    }

    @Test
    void whenReceiveEntity_thenReceiveDomain(){
        List<OrderEntity> entities = faker.entity().getOrderEntityBot().build(1);
        OrderItemEntity orderItem = faker.entity()
                .getOrderItemEntityBot()
                .withOrder(() -> entities.get(0))
                .build();
        entities.get(0).setOrderItems(new HashSet<>(Collections.singletonList(orderItem)));

        List<OrderInsertedDomain> actual = orderMapper.toDomain(entities);

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(entities.size());
        OrderInsertedDomain domain = actual.get(0);
        OrderEntity entity = entities.get(0);
        assertThat(domain).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt", "externalCreatedAt",
                        "discount", "subTotal", "total", "orderItems", "items", "id", "client", "clientId")
                .isEqualTo(entity);
        assertThat(domain.getCreatedAt()).isEqualTo(entity.getExternalCreatedAt());
        assertThat(domain.getClientId()).isEqualTo(entity.getClient().getId());
        assertThat(domain.getItems()).hasSize(entity.getOrderItems().size());
        assertThat(domain.getTotal()).usingComparator(BigDecimal::compareTo).isEqualTo(entity.getTotal());
        assertThat(domain.getSubTotal()).usingComparator(BigDecimal::compareTo).isEqualTo(entity.getSubTotal());
        assertThat(domain.getDiscount()).usingComparator(BigDecimal::compareTo).isEqualTo(entity.getDiscount());

        OrderItemInsertedDomain itemDomain = domain.getItems().get(0);
        OrderItemEntity orderItemEntity = Arrays.asList(entity.getOrderItems().toArray(new OrderItemEntity[0])).get(0);
        assertThat(itemDomain).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt", "amount")
                .isEqualTo(orderItemEntity.getItem());
        assertThat(itemDomain.getAmount()).isEqualTo(orderItemEntity.getAmount());
        assertThat(itemDomain.getId()).isEqualTo(orderItemEntity.getItem().getId());
    }

    @Test
    void whenReceiveFilterDomain_thenReturnFilterDTO(){
        FindAllOrderFilterDomain domain = faker.domainApp().getFindAllOrderFilterDomainBot().build();
        FindAllOrderFilterDTO actual = orderMapper.toDTO(domain);

        assertThat(actual).usingRecursiveComparison().isEqualTo(domain);
    }

    @Test
    void whenReceiveFindAllOrderDomain_thenReturnResultDTO(){
        List<FindAllOrderResultDTO> dto = faker.entity().getFindAllOrderResultDTOBot().build(1);
        List<FindAllOrderDomain> actual = orderMapper.toFindAllDomain(dto);

        assertThat(actual).hasSize(dto.size());
        FindAllOrderDomain domain = actual.get(0);
        FindAllOrderResultDTO resultDTO = dto.get(0);
        assertThat(domain).usingRecursiveComparison()
                .ignoringFields("createdAt", "externalCreatedAt")
                .isEqualTo(resultDTO);
        assertThat(domain.getCreatedAt()).isEqualTo(resultDTO.getExternalCreatedAt());
    }

}
