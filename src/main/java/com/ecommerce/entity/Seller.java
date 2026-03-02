package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Seller entity for individual/business sellers.
 */
@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String sellerCode;

    @Column(nullable = false)
    private String storeName;

    @Column(length = 1000)
    private String storeDescription;

    private String businessLicenseNumber;

    private String taxId;

    @Column(length = 500)
    private String businessAddress;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String phone;

    private String email;

    @Column(length = 500)
    private String logoUrl;

    @Column(length = 500)
    private String bannerUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal commissionRate = BigDecimal.valueOf(10.0);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SellerTier tier = SellerTier.BASIC;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SellerStatus status = SellerStatus.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalProducts = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalOrders = 0;

    @Column(nullable = false)
    @Builder.Default
    private Double averageRating = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalReviews = 0;

    private LocalDate storeOpenDate;

    @Column(length = 2000)
    private String returnPolicy;

    @Column(length = 2000)
    private String shippingPolicy;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isVerified = false;

    @Column(length = 500)
    private String verificationDocuments;

    @ElementCollection
    @CollectionTable(name = "seller_categories", joinColumns = @JoinColumn(name = "seller_id"))
    @Column(name = "category_id")
    @Builder.Default
    private List<String> categories = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SellerProduct> sellerProducts = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SellerOrder> sellerOrders = new ArrayList<>();

    public void addSellerProduct(SellerProduct product) {
        sellerProducts.add(product);
        product.setSeller(this);
        totalProducts = sellerProducts.size();
    }

    public void removeSellerProduct(SellerProduct product) {
        sellerProducts.remove(product);
        product.setSeller(null);
        totalProducts = sellerProducts.size();
    }

    public void addSellerOrder(SellerOrder order) {
        sellerOrders.add(order);
        order.setSeller(this);
        totalOrders = sellerOrders.size();
    }

    public void removeSellerOrder(SellerOrder order) {
        sellerOrders.remove(order);
        order.setSeller(null);
        totalOrders = sellerOrders.size();
    }
}
