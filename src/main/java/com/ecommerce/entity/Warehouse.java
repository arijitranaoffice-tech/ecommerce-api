package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Warehouse entity for distributor warehouse management.
 */
@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    @Column(nullable = false)
    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;

    private String email;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DistributorInventory> inventories = new ArrayList<>();

    // Note: transfers relationship removed as InventoryTransfer has fromWarehouse and toWarehouse
}
