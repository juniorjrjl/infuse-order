package br.com.infuse.adapter.outbound.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, name = "control_id")
    private Long controlId;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(nullable = false, name = "sub_total")
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_orders_clients"))
    private ClientEntity client;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = PERSIST)
    private Set<OrderItemEntity> orderItems;

    @Column(nullable = false, name = "external_created_at")
    private OffsetDateTime externalCreatedAt;

    @Column(nullable = false, name = "created_at")
    private OffsetDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        if (externalCreatedAt == null) externalCreatedAt = OffsetDateTime.now();

        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void  preUpdate(){
        updatedAt = OffsetDateTime.now();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity entity = (OrderEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(controlId, entity.controlId) && Objects.equals(discount, entity.discount) && Objects.equals(subTotal, entity.subTotal) && Objects.equals(total, entity.total) && Objects.equals(client, entity.client) && Objects.equals(externalCreatedAt, entity.externalCreatedAt) && Objects.equals(createdAt, entity.createdAt) && Objects.equals(updatedAt, entity.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, controlId, discount, subTotal, total, client, externalCreatedAt, createdAt, updatedAt);
    }
}
