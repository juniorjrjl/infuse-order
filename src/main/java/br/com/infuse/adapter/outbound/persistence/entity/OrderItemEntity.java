package br.com.infuse.adapter.outbound.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import static javax.persistence.CascadeType.MERGE;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORDERS_ITEMS")
public class OrderItemEntity {

    @EmbeddedId
    private OrderItemId id;

    @ToString.Exclude
    @ManyToOne(optional = false, cascade = MERGE)
    @MapsId("item")
    @JoinColumn(name = "item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_items_orders_items"), insertable = false, updatable = false)
    private ItemEntity item;

    @ToString.Exclude
    @ManyToOne(optional = false, cascade = MERGE)
    @MapsId("order")
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_orders_orders_items"), insertable = false, updatable = false)
    private OrderEntity order;

    @Column(nullable = false)
    private Long amount;

}
