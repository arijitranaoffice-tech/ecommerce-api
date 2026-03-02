package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DistributorInventory entity for tracking distributor stock levels.
 */
@Entity
@Table(name = "distributor_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributorInventory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_id", nullable = false)
    private Distributor distributor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantityOnHand = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantityAvailable = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantityReserved = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantityInTransit = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer reorderPoint = 10;

    @Column(nullable = false)
    @Builder.Default
    private Integer reorderQuantity = 50;

    private LocalDateTime lastRestockedAt;

    private String location;
    private String bin;

    @Column(length = 500)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private InventoryStatus status = InventoryStatus.ACTIVE;
}
