package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Distributor entity representing distribution partners.
 */
@Entity
@Table(name = "distributors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distributor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String distributorCode;

    @Column(nullable = false)
    private String companyName;

    private String businessLicenseNumber;

    private String taxId;

    @Column(length = 1000)
    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal commissionRate = BigDecimal.valueOf(5.0);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DistributorTier tier = DistributorTier.BRONZE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DistributorStatus status = DistributorStatus.PENDING;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal creditLimit = BigDecimal.valueOf(10000.0);

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal currentBalance = BigDecimal.ZERO;

    private LocalDate contractStartDate;

    private LocalDate contractEndDate;

    @Column(length = 2000)
    private String territoryDescription;

    @ElementCollection
    @CollectionTable(name = "distributor_territories", joinColumns = @JoinColumn(name = "distributor_id"))
    @Column(name = "territory_code")
    @Builder.Default
    private List<String> territories = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DistributorProduct> distributorProducts = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DistributorOrder> distributorOrders = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DistributorCommission> commissions = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DistributorInventory> inventories = new ArrayList<>();

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DistributorCustomer> customers = new ArrayList<>();

    public void addDistributorProduct(DistributorProduct product) {
        distributorProducts.add(product);
        product.setDistributor(this);
    }

    public void removeDistributorProduct(DistributorProduct product) {
        distributorProducts.remove(product);
        product.setDistributor(null);
    }

    public void addDistributorOrder(DistributorOrder order) {
        distributorOrders.add(order);
        order.setDistributor(this);
    }

    public void removeDistributorOrder(DistributorOrder order) {
        distributorOrders.remove(order);
        order.setDistributor(null);
    }

    public void addCommission(DistributorCommission commission) {
        commissions.add(commission);
        commission.setDistributor(this);
    }

    public void removeCommission(DistributorCommission commission) {
        commissions.remove(commission);
        commission.setDistributor(null);
    }

    public void addInventory(DistributorInventory inventory) {
        inventories.add(inventory);
        inventory.setDistributor(this);
    }

    public void removeInventory(DistributorInventory inventory) {
        inventories.remove(inventory);
        inventory.setDistributor(null);
    }

    public void addCustomer(DistributorCustomer customer) {
        customers.add(customer);
        customer.setDistributor(this);
    }

    public void removeCustomer(DistributorCustomer customer) {
        customers.remove(customer);
        customer.setDistributor(null);
    }
}
