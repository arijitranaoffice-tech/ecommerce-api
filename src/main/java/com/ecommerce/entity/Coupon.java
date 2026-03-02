package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Coupon entity for discount codes.
 */
@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DiscountType discountType = DiscountType.PERCENTAGE;

    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal discountValue = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal minPurchaseAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(nullable = false)
    @Builder.Default
    private Integer usageLimit = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer usedCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer usageLimitPerUser = 1;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    private LocalDate validFrom;

    private LocalDate validUntil;

    @ElementCollection
    @CollectionTable(name = "coupon_applicable_categories", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "category_id")
    @Builder.Default
    private List<String> applicableCategories = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "coupon_applicable_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    @Builder.Default
    private List<String> applicableProducts = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "coupon_excluded_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    @Builder.Default
    private List<String> excludedProducts = new ArrayList<>();

    @Column(length = 500)
    private String termsAndConditions;

    private LocalDateTime redeemedAt;
}
