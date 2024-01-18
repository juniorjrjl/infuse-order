package br.com.infuse.adapter.outbound.persistence;

import br.com.infuse.adapter.mapper.repository.OrderMapper;
import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderFilterDTO;
import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderResultDTO;
import br.com.infuse.adapter.outbound.persistence.entity.ClientEntity;
import br.com.infuse.adapter.outbound.persistence.entity.ClientEntity_;
import br.com.infuse.adapter.outbound.persistence.entity.ItemEntity;
import br.com.infuse.adapter.outbound.persistence.entity.ItemEntity_;
import br.com.infuse.adapter.outbound.persistence.entity.OrderEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderEntity_;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemEntity;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemEntity_;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemId;
import br.com.infuse.adapter.outbound.persistence.entity.OrderItemId_;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.port.OrderPort;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class OrderRepository implements OrderPort {

    private final EntityManager entityManager;
    private final OrderMapper mapper;

    @Override
    public List<OrderInsertedDomain> save(final List<OrderInsertDomain> domains) {
        entityManager.getTransaction().begin();
        for (int i = 0; i < domains.size(); i++) {
            List<OrderItemEntity> orderItems = mapper.toItemEntity(domains.get(i).getItems());
            for (int j = 0; j < orderItems.size(); j++) {
                entityManager.persist(orderItems.get(j).getItem());
            }
            OrderEntity order = mapper.toEntity(domains.get(i));
            entityManager.persist(order);
            for (int j = 0; j < orderItems.size(); j++) {
                orderItems.get(j).setOrder(order);
                orderItems.get(j).setId(new OrderItemId(order.getId(), orderItems.get(j).getItem().getId()));
                entityManager.persist(orderItems.get(j));
            }
        }
        entityManager.getTransaction().commit();
        List<Long> controlIds = domains.stream().map(OrderInsertDomain::getControlId).collect(Collectors.toList());
        List<OrderEntity> entities = findByControlIds(controlIds);
        for (int i = 0; i < entities.size(); i++) {
            entities.get(0).setOrderItems(findItemsByOrder(entities.get(0).getId()));
        }
        return mapper.toDomain(entities);
    }

    private OrderEntity findById(final Long id){
        return entityManager.find(OrderEntity.class, id);
    }

    @Override
    public List<FindAllOrderDomain> findAll(final FindAllOrderFilterDomain domain) {
        FindAllOrderFilterDTO dto = mapper.toDTO(domain);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FindAllOrderResultDTO> criteriaQuery = builder.createQuery(FindAllOrderResultDTO.class);
        Root<OrderEntity> root = criteriaQuery.from(OrderEntity.class);
        Join<OrderEntity, ClientEntity> clientJoin = root.join(OrderEntity_.client);

        Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
        Root<OrderItemEntity> subRoot = subQuery.from(OrderItemEntity.class);;
        subQuery.select(builder.sum(subRoot.get(OrderItemEntity_.amount)));
        subQuery.where(
                builder.equal(subRoot.get(OrderItemEntity_.id).get(OrderItemId_.orderId),
                        root.get(OrderEntity_.id))
        );

        criteriaQuery.multiselect(
                root.get(OrderEntity_.id),
                root.get(OrderEntity_.controlId),
                root.get(OrderEntity_.discount),
                root.get(OrderEntity_.subTotal),
                root.get(OrderEntity_.total),
                root.get(OrderEntity_.client).get(ClientEntity_.id),
                root.get(OrderEntity_.externalCreatedAt),
                subQuery.getSelection()
        );
        if(dto.hasSomeFilter()){
            buildWhereClause(dto, builder, root, criteriaQuery, clientJoin, subRoot, subQuery);
        }
        TypedQuery<FindAllOrderResultDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<FindAllOrderResultDTO> dtos = typedQuery.getResultList();
        return mapper.toFindAllDomain(dtos);
    }

    private static void buildWhereClause(final FindAllOrderFilterDTO dto, final CriteriaBuilder builder,
                                         final Root<OrderEntity> root, final CriteriaQuery<FindAllOrderResultDTO> criteriaQuery,
                                         final Join<OrderEntity, ClientEntity> clientJoin, final Root<OrderItemEntity> subRoot,
                                         final Subquery<Long> subQuery) {
        List<Predicate> wherePredicates = new ArrayList<>();
        if (dto.getControlId() != null){
            wherePredicates.add(builder.equal(root.get(OrderEntity_.controlId), dto.getControlId()));
        }
        if (dto.productNameIsNotBlank()){
            Join<OrderEntity, OrderItemEntity> orderItemRoot = root.join(OrderEntity_.orderItems);
            Join<OrderItemEntity, ItemEntity> itemRoot = orderItemRoot.join(OrderItemEntity_.item);
            wherePredicates.add(builder.like(itemRoot.get(ItemEntity_.name), dto.getLikeName()));
        }
        if (dto.useMinOrMaxValueFilter()){
            if (dto.useMinAndMaxValueFilter()){
                Predicate totalPredicate = builder.and(
                        builder.greaterThanOrEqualTo(root.get(OrderEntity_.total), dto.getMinValue()),
                        builder.lessThanOrEqualTo(root.get(OrderEntity_.total), dto.getMaxValue())
                );
                Predicate subTotalPredicate = builder.and(
                        builder.greaterThanOrEqualTo(root.get(OrderEntity_.subTotal), dto.getMinValue()),
                        builder.lessThanOrEqualTo(root.get(OrderEntity_.subTotal), dto.getMaxValue())
                );
                wherePredicates.add(builder.or(totalPredicate, subTotalPredicate));
            } else if (dto.getMinValue() == null){
                wherePredicates.add(builder.lessThanOrEqualTo(root.get(OrderEntity_.total), dto.getMaxValue()));
                wherePredicates.add(builder.lessThanOrEqualTo(root.get(OrderEntity_.subTotal), dto.getMaxValue()));
            }else{
                wherePredicates.add(builder.greaterThanOrEqualTo(root.get(OrderEntity_.total), dto.getMinValue()));
                wherePredicates.add(builder.greaterThanOrEqualTo(root.get(OrderEntity_.subTotal), dto.getMinValue()));
            }
        }
        if (dto.getClientId() != null){
            wherePredicates.add(builder.equal(clientJoin.get(ClientEntity_.id), dto.getClientId()));
        }
        if (dto.useMinOrMaxCreatedAtFilter()){
            if (dto.useMinAndMaxCreatedAtFilter()){
                wherePredicates.add(builder.and(
                        builder.greaterThanOrEqualTo(root.get(OrderEntity_.createdAt),dto.getMinCreatedAt()),
                        builder.lessThanOrEqualTo(root.get(OrderEntity_.createdAt),dto.getMaxCreatedAt())
                ));
            }else if (dto.getMinCreatedAt() == null){
                wherePredicates.add(builder.lessThanOrEqualTo(root.get(OrderEntity_.createdAt),dto.getMaxCreatedAt()));
            } else {
                wherePredicates.add(builder.greaterThanOrEqualTo(root.get(OrderEntity_.createdAt),dto.getMinCreatedAt()));
            }
        }
        if (dto.useMinOrMaxAmountFilter()){
            if (dto.useMinAndMaxAmountFilter()){
                wherePredicates.add(builder.and(
                        builder.greaterThanOrEqualTo(subQuery, dto.getMaxAmount()),
                        builder.lessThanOrEqualTo(subQuery, dto.getMaxAmount())
                ));
            } else if (dto.getMinAmount() == null){
                wherePredicates.add(builder.lessThanOrEqualTo(subQuery, dto.getMaxAmount()));
            } else {
                wherePredicates.add(builder.greaterThanOrEqualTo(subQuery, dto.getMinAmount()));
            }
        }
        if (!wherePredicates.isEmpty()) {
            criteriaQuery.where(wherePredicates.toArray(new Predicate[0]));
        }
    }

    @Override
    public boolean hasControlId(final Long controlId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = builder.createQuery(OrderEntity.class);
        Root<OrderEntity> root = criteriaQuery.from(OrderEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get(OrderEntity_.controlId), controlId));
        TypedQuery<OrderEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        boolean found = true;
        try {
            typedQuery.getSingleResult();
        }catch (NoResultException ex){
            found = false;
        }
        return found;
    }

    private List<OrderEntity> findByControlIds(final List<Long> controlIds){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = builder.createQuery(OrderEntity.class);
        Root<OrderEntity> root = criteriaQuery.from(OrderEntity.class);
        Join<OrderEntity, ClientEntity> clientJoin = root.join(OrderEntity_.client);

        criteriaQuery.select(root);
        criteriaQuery.where(root.get(OrderEntity_.controlId).in(controlIds));
        TypedQuery<OrderEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    private Set<OrderItemEntity> findItemsByOrder(final Long orderId){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderItemEntity> criteriaQuery = builder.createQuery(OrderItemEntity.class);
        Root<OrderItemEntity> root = criteriaQuery.from(OrderItemEntity.class);
        Join<OrderItemEntity, ItemEntity> itemJoin = root.join(OrderItemEntity_.item);

        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get(OrderItemEntity_.id).get(OrderItemId_.orderId), orderId));
        TypedQuery<OrderItemEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultStream().collect(Collectors.toSet());
    }

}
